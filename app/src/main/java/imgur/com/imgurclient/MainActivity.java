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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
    ListView draw_list;
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
    private ImageLoader imageHelper;
    private UserLoader userHelper;
    UserLoader userLoader;
    ImageLoader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = new ImgurAuthentication();
        imageHelper = new GetTopPosts();
        getTopPosts(0);
        getUserInformation();
        populateList();
        initializeVariables();
        getMyPosts();
        this.setNavigationDrawer(adapter);
        swipeRefresh();

        loader = obtainLoader(getIntent().getExtras());
    }

    private ImageLoader obtainLoader(Bundle extras) {
        if (extras == null) {
            return new GetTopPosts();
        }
        return new ImageLoader() {
            @Override
            public void load(Callback2 call) {
                call.onFailure();
            }
        };
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
        getTopPosts(0);
        imageAdapter.updateAfterRefresh();
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, "Nothing to show,posts are up to date", Toast.LENGTH_SHORT).show();
    }

    public void showDialog(final ImageModel imageResponse) {
        AlertDialog dialog = buildDialog();

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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isTopPosts", true);
    }


    public void setNavigationDrawer(NavigationAdapter adapter) {
        draw_list.setAdapter(adapter);
        draw_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
                mDrawerLayout.closeDrawers();
            }
        });
    }

    public void getTopPosts(int page) {

        imageHelper.load(new ImageLoader.Callback2() {
            @Override
            public void onSuccess(List<ImageModel> images) {
                inflatePosts(images);
            }

            @Override
            public void onFailure() {
                Log.e(MainActivity.class.getName(), "Get Top Posts failure");
            }
        });

    }

    public void getMyPosts() {
        imageHelper.load(new ImageLoader.Callback2() {
            @Override
            public void onSuccess(List<ImageModel> images) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    /*public void getMyPosts() {
        ImgurAPI api = ServiceGenerator.createService(ImgurAPI.class);
        Call<ImgurResponse<List<ImageResponse>>> call = api.getMyPosts(auth.getHeader(), id);
        call.enqueue(new Callback<ImgurResponse<List<ImageResponse>>>() {
            @Override
            public void onResponse(Call<ImgurResponse<List<ImageResponse>>> call, Response<ImgurResponse<List<ImageResponse>>> response) {
                ImgurResponse<List<ImageResponse>> iResponse = response.body();
                if (response.isSuccessful()) {
                    for (ImageResponse imageResponse : iResponse.data) {

                        if (imageResponse.getType() != null && imageResponse.isAnimated()) {

                            myPosts.add(0, imageResponse);

                        }
                    }

                } else {

                    Log.e(MainActivity.class.getName(), response.message() + " My posts");
                }
            }

            @Override
            public void onFailure(Call<ImgurResponse<List<ImageResponse>>> call, Throwable t) {


                t.printStackTrace();

            }
        });
    }*/

    public void getUserInformation() {
        ImgurAPI api = ServiceGenerator.createService(ImgurAPI.class);

        Call<ImgurResponse<UserResponse>> call = api.getUserInfo("EmkaMK");
        call.enqueue(new Callback<ImgurResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ImgurResponse<UserResponse>> call, Response<ImgurResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    id = response.body().data.getId();

                } else
                    Log.e(MainActivity.class.getName(), "Response not successful");
            }

            @Override
            public void onFailure(Call<ImgurResponse<UserResponse>> call, Throwable t) {

                Log.e(MainActivity.class.getName(), "Failure");
                t.printStackTrace();

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
        draw_list = (ListView) findViewById(R.id.navList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        adapter = new NavigationAdapter(this, items);

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
                setContentView(R.layout.activity_home);
                break;
        }

    }

    private void inflatePosts(List<ImageModel> images) {
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager manager;
        if (getResources().getConfiguration().orientation == 1) {
            manager = new GridLayoutManager(MainActivity.this, 2);
        } else {
            manager = new GridLayoutManager(MainActivity.this, 3);
        }
        rView.setLayoutManager(manager);
        imageAdapter = new ImageAdapter(this, images);
        rView.setAdapter(imageAdapter);
        rView.addOnScrollListener(new EndlessRecyclerViewScrollListener((GridLayoutManager) manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getTopPosts(page);

            }
        });

    }


}
