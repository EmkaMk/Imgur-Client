package imgur.com.imgurclient.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import imgur.com.imgurclient.EndlessRecyclerViewScrollListener;
import imgur.com.imgurclient.GetMyPosts;
import imgur.com.imgurclient.GetTopPosts;
import imgur.com.imgurclient.ImageAdapter;
import imgur.com.imgurclient.ImageLoader;
import imgur.com.imgurclient.R;
import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.ServiceGenerator;
import imgur.com.imgurclient.login.ImgurAuthentication;
import imgur.com.imgurclient.models.ImageService.AuthorizationResponse;
import imgur.com.imgurclient.models.ImageService.ImageModel;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import imgur.com.imgurclient.models.ImageService.UserResponse;
import imgur.com.imgurclient.navigationDrawer.NavDrawerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements NavDrawerView.MenuSelection {
    private ImgurAuthentication auth;
    private ImageView image;
    private TextView title, description, views, setDescription;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageAdapter imageAdapter;
    private RecyclerView rView;
    private ImageLoader postsLoader;
    private RecyclerView.LayoutManager manager;
    private TextView userName;
    private NavDrawerView navDrawerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navDrawerView= (NavDrawerView) findViewById(R.id.navList);
        setupNavDrawer();
        imageAdapter = ImageAdapter.getInstance();
        postsLoader = new GetTopPosts();
        auth = ImgurAuthentication.getInstance();
        auth.setModel(AuthorizationResponse.getInstance());
        setupRecyclerView();
        //getUserInformation();
        getTopPosts(0);
        swipeRefresh();

    }


    public void swipeRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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
                final int size = 512;

                title.setText(imageResponse.getTitle());
                Picasso.with(getApplicationContext())
                        .load(imageResponse.getLink())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .resize(size, size)
                        .onlyScaleDown()
                        .into(image);

                if (imageResponse.getDescription() != null) {
                    setDescription.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    setDescription.setText("Description: ");
                }
                description.setText(imageResponse.getDescription());
                views.setText("Views: " + imageResponse.getViews());

            }
        });

        dialog.show();
    }

    public AlertDialog buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog, null);
        image = (ImageView) dialogLayout.findViewById(R.id.imageView);
        title = (TextView) dialogLayout.findViewById(R.id.title);
        description = (TextView) dialogLayout.findViewById(R.id.description);
        views = (TextView) dialogLayout.findViewById(R.id.views);
        setDescription = (TextView) dialogLayout.findViewById(R.id.fitDescription);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    public void getTopPosts(int page) {
        postsLoader.load(new ImageLoader.Callback2() {
            @Override
            public void onSuccess(List<ImageModel> images) throws IOException {
                imageAdapter.addImages(images);
                //saveImages((ArrayList<ImageModel>) images);
            }

            @Override
            public void onFailure() {
                Log.e(MainActivity.class.getName(), "Get Top Posts failure");
            }
        }, page);

    }

    public void getMyPosts(ImageLoader loader) {
        loader.load(new ImageLoader.Callback2() {
            @Override
            public void onSuccess(List<ImageModel> images) throws IOException {
                imageAdapter.addMyImages(images);
            }

            @Override
            public void onFailure() {

                Log.e(MainActivity.class.getName(), "Get My Posts failure");

            }
        }, 0);
    }

    public boolean tryRefresh() {
        return postsLoader.loadRefreshed(new ImageLoader.Callback2() {
            @Override
            public void onSuccess(List<ImageModel> images) {
                imageAdapter.addImages(images);
                Log.e(MainActivity.class.getName(), "Refresh is called!");
            }

            @Override
            public void onFailure() {
                Log.e(MainActivity.class.getName(), "No refreshed");
                Toast.makeText(MainActivity.this, "Nothing to update", LENGTH_SHORT).show();
            }
        });
    }

    public void getUserInformation() {
        ImgurAPI api = ServiceGenerator.createService(ImgurAPI.class);
        Call<ImgurResponse<UserResponse>> call = api.getUserInfo(AuthorizationResponse.getInstance().getAccount_username());
        call.enqueue(new Callback<ImgurResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ImgurResponse<UserResponse>> call, Response<ImgurResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Logged in!", Toast.LENGTH_LONG).show();
                    //id = response.body().data.getId();
                } else {

                    Log.e(MainActivity.class.getName(), response.message());
                    Toast.makeText(MainActivity.this, "Login is not successfull! Please try again!", LENGTH_SHORT).show();
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
        manager = new GridLayoutManager(MainActivity.this,
                getResources().getInteger(R.integer.posts_grid_cols_count));

        rView.setLayoutManager(manager);
        imageAdapter.setContext(this);
        rView.setAdapter(imageAdapter);
        if (!imageAdapter.getResponse().isEmpty()) {
            imageAdapter.getResponse().clear();
        }

        rView.addOnScrollListener(new EndlessRecyclerViewScrollListener((GridLayoutManager) manager, postsLoader) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (postsLoader instanceof GetTopPosts) {
                    getTopPosts(page);
                } else
                    getMyPosts(postsLoader);
            }
        });
    }

    private void setupNavDrawer() {
        userName = (TextView) findViewById(R.id.userName);
        userName.setText(AuthorizationResponse.getInstance().getAccount_username());

        navDrawerView.setMenuSelection(this);
        //findViewById(R.id.drawerLayout);
       navDrawerView.setDrawerLayout((DrawerLayout) findViewById(R.id.drawerLayout));

    }

    @Override
    public void onUploadSelected() {
        startActivity(new Intent(this, UploadActivity.class));
    }


    @Override
    public void onMyPostsSelected() {

        getMyPosts(new GetMyPosts());


    }

    @Override
    public void onLogoutSelected() {

        auth.logOut();
        startActivity(new Intent(this, LoginActivity.class).setAction("CUSTOM_ACTION"));
        Toast.makeText(this, "Logged out", LENGTH_SHORT).show();

    }
}
