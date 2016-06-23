package imgur.com.imgurclient;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import imgur.com.imgurclient.login.ImgurAuthentication;

public class MainActivity extends AppCompatActivity {
    ListView draw_list;
    ImgurAuthentication auth;
    RelativeLayout draw_layout;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavigationItem> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=new ImgurAuthentication();
        items.add(new NavigationItem("Upload new photo", R.mipmap.ic_launcher));
        items.add(new NavigationItem("My posts",R.mipmap.ic_launcher));
        items.add(new NavigationItem("Log out", R.mipmap.ic_launcher));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        draw_layout = (RelativeLayout) findViewById(R.id.drawerPane);
        draw_list = (ListView) findViewById(R.id.navList);
        NavigationAdapter adapter = new NavigationAdapter(this, items);
        draw_list.setAdapter(adapter);
        draw_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectItemFromDrawer(int position) {

        Intent i;
        switch (position)
        {
            case 0:
                startActivity(new Intent(this,UploadActivity.class));
                break;
            case 1:
                startActivity(new Intent(this,ImagesTest.class));
                break;

            case 2:
                auth.logOut();
                setContentView(R.layout.activity_home);
               // startActivity(new Intent(this,HomeActivity.class));

                break;


        }

        /*
        Fragment fragment = new PreferencesFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        draw_list.setItemChecked(position, true);
        setTitle(items.get(position).title);

        // Close the drawer
        mDrawerLayout.closeDrawer(draw_layout);*/
    }

    class NavigationItem {
        private String title;
        private int image;

        public NavigationItem(String title, int image) {
            this.title = title;
            this.image = image;
        }

    }

    class NavigationAdapter extends BaseAdapter {
        Context context;
        List<NavigationItem> items;

        public NavigationAdapter(Context context, List<NavigationItem> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView image = (ImageView) view.findViewById(R.id.icon);

           title.setText(items.get(position).title);
            image.setImageResource(items.get(position).image);
            return view;
        }
    }
}
