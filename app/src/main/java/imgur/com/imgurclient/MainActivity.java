package imgur.com.imgurclient;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.login.ImgurAuthentication;
import imgur.com.imgurclient.models.ImageService.ImageResponse;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
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
    private ArrayList<String> imageLinks = new ArrayList<>();
    private TreeMap<String,List<String>> attributes=new TreeMap<>();
    NavigationAdapter adapter;
    private List<ImageResponse> finalResponse=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = new ImgurAuthentication();
        getTopPosts();
        populateList();
        initializeList();
        adapter = new NavigationAdapter(this, items);
        this.setNavigationDrawer(adapter);
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

    public void getTopPosts() {
        ImgurAPI api = ServiceGenerator.createService(ImgurAPI.class);
        Call<ImgurResponse<List<ImageResponse>>> call = api.getImages(auth.getHeader());
        call.enqueue(new Callback<ImgurResponse<List<ImageResponse>>>() {
            @Override
            public void onResponse(Call<ImgurResponse<List<ImageResponse>>> call, Response<ImgurResponse<List<ImageResponse>>> response) {
                if (response.isSuccessful()) {
                    getImageLinks(response);
                    inflateTopPosts();

                } else
                    Log.e(MainActivity.class.getName(), response.message());
            }

            @Override
            public void onFailure(Call<ImgurResponse<List<ImageResponse>>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void populateList() {
        items.add(new NavigationItem("Upload new photo", R.mipmap.ic_launcher));
        items.add(new NavigationItem("My posts", R.mipmap.ic_launcher));
        items.add(new NavigationItem("Log out", R.mipmap.ic_launcher));
    }

    private void initializeList() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        draw_layout = (RelativeLayout) findViewById(R.id.drawerPane);
        draw_list = (ListView) findViewById(R.id.navList);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectItemFromDrawer(int position) {

        switch (position) {
            case 0:
                startActivity(new Intent(this, UploadActivity.class));
                break;
            case 1:
                inflateTopPosts();
                break;

            case 2:
                auth.logOut();
                setContentView(R.layout.activity_home);
                break;
        }

    }

    private void inflateTopPosts() {
        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager manager;
        if (getResources().getConfiguration().orientation == 1) {
            manager = new GridLayoutManager(MainActivity.this, 2);
        } else {
            manager = new GridLayoutManager(MainActivity.this, 3);
        }
        rView.setLayoutManager(manager);
        rView.setAdapter(new ImageAdapter(this,finalResponse));
    }

    protected void getImageLinks(Response<ImgurResponse<List<ImageResponse>>> response) {
        ImgurResponse<List<ImageResponse>> iResponse = response.body();
        ArrayList<String> list=new ArrayList<>();

        for (ImageResponse imageResponse : iResponse.data) {

            if (imageResponse.getType() != null && imageResponse.isAnimated()) {

                finalResponse.add(imageResponse);

            }
        }

    }

}
