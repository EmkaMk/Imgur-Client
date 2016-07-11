package imgur.com.imgurclient;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.login.ImgurAuthentication;
import imgur.com.imgurclient.models.ImageService.ImageModel;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import imgur.com.imgurclient.models.ImageService.UserResponse;
import imgur.com.imgurclient.navigationDrawer.NavigationAdapter;
import imgur.com.imgurclient.navigationDrawer.NavigationItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ListView drawList;
    ImgurAuthentication auth;
    RelativeLayout draw_layout;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavigationItem> items = new ArrayList<>();
    NavigationAdapter adapter;
    private ImageView image;
    private TextView title, description, views, setDescription;
    private SwipeRefreshLayout swipeRefreshLayout;
    ImageAdapter imageAdapter;
    TextView userName;
    int id;
    private RecyclerView rView;
    ImageLoader loaderTopPosts;
    private RecyclerView.LayoutManager manager;
    private ImageSaver saveImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();
        auth = new ImgurAuthentication();
        setupRecyclerView();
        getTopPosts(0);
        getUserInformation();
        populateList();
        this.setNavigationDrawer(adapter);
        swipeRefresh();

    }


    private ImageLoader obtainTopPostsLoader() {
        return new GetTopPosts();
    }

    public void swipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshUpdate();
            }
        });
    }

    public void onRefreshUpdate() {
        if (tryRefresh()) {
            imageAdapter.updateAfterRefresh();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showDialog(final ImageModel imageResponse) {
        AlertDialog dialog = buildDialog();
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                title.setText(imageResponse.getTitle());
                Picasso.with(getApplicationContext()).load(imageResponse.getLink()).into(image);
                if (imageResponse.getDescription() != null) {
                    setDescription.setText("Description: ");
                }
                description.setText(imageResponse.getDescription());
                views.setText(imageResponse.getViews() + " views");

            }
        });

        dialog.show();
    }

    public AlertDialog buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.activity_show_image, null);
        image = (ImageView) dialogLayout.findViewById(R.id.imageView);
        title = (TextView) dialogLayout.findViewById(R.id.title);
        description = (TextView) dialogLayout.findViewById(R.id.description);
        views = (TextView) dialogLayout.findViewById(R.id.views);
        setDescription = (TextView) dialogLayout.findViewById(R.id.fitDescription);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setNavigationDrawer(NavigationAdapter adapter) {
        drawList.setAdapter(adapter);
        drawList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
                mDrawerLayout.closeDrawers();
            }
        });
    }

    public void getTopPosts(int page) {
        loaderTopPosts.load(new ImageLoader.Callback2() {
            @Override
            public void onSuccess(List<ImageModel> images) throws IOException {
                imageAdapter.addImages(images);
                saveImages((ArrayList<ImageModel>) images);
            }

            @Override
            public void onFailure() {
                Log.e(MainActivity.class.getName(), "Get Top Posts failure");
            }
        }, page);

    }

    public boolean tryRefresh() {
        return loaderTopPosts.loadRefreshed(new ImageLoader.Callback2() {
            @Override
            public void onSuccess(List<ImageModel> images) {
                imageAdapter.addImages(images);
            }

            @Override
            public void onFailure() {
                Log.e(MainActivity.class.getName(), "No refreshed");
                Toast.makeText(MainActivity.this, "Nothing to update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserInformation() {
        ImgurAPI api = ServiceGenerator.createService(ImgurAPI.class);
        Call<ImgurResponse<UserResponse>> call = api.getUserInfo("EmkaMK");
        call.enqueue(new Callback<ImgurResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ImgurResponse<UserResponse>> call, Response<ImgurResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    id = response.body().data.getId();
                } else {
                    Log.e(MainActivity.class.getName(), "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<ImgurResponse<UserResponse>> call, Throwable t) {

                Log.e(MainActivity.class.getName(), "Failure");
                t.printStackTrace();

            }
        });
    }

    private void setupRecyclerView() {
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        if (getResources().getConfiguration().orientation == 1) {
            manager = new GridLayoutManager(MainActivity.this, 2);
        } else {
            manager = new GridLayoutManager(MainActivity.this, 3);
        }

        rView.setLayoutManager(manager);
        imageAdapter = new ImageAdapter(this);
        rView.setAdapter(imageAdapter);

        rView.addOnScrollListener(new EndlessRecyclerViewScrollListener((GridLayoutManager) manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getTopPosts(page);
            }
        });
    }

    private void populateList() {
        userName = (TextView) findViewById(R.id.userName);
        userName.setText("EmkaMK");
        items.add(new NavigationItem("Upload new photo", R.mipmap.ic_launcher));
        items.add(new NavigationItem("My posts", R.mipmap.ic_launcher));
        items.add(new NavigationItem("Log out", R.mipmap.ic_launcher));
    }

    private void initializeVariables() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        draw_layout = (RelativeLayout) findViewById(R.id.drawerPane);
        drawList = (ListView) findViewById(R.id.navList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        adapter = new NavigationAdapter(this, items);
        loaderTopPosts = obtainTopPostsLoader();
        saveImage = new InternalStorageSaver();

    }

    private void saveImages(ArrayList<ImageModel> images) throws IOException {
        // saveImage.saveImages(images,this);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectItemFromDrawer(int position) {

        switch (position) {
            case 0:
                startActivity(new Intent(this, UploadActivity.class));
                break;
            case 1:
                // inflatePosts();
                break;
            case 2:
                auth.logOut();
                startActivity(new Intent(this, HomeActivity.class).setAction("CUSTOM_ACTION"));
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
        }

    }


}
