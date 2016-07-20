package imgur.com.imgurclient;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import imgur.com.imgurclient.navigationDrawer.NavigationAdapter;

/**
 * Created by Emilija.Pereska on 7/18/2016.
 */
public class NavDrawerLoader extends ComponentLoader {

    private ListView drawList;
    private DrawerLayout mDrawerLayout;
    private View drawerView;
    private NavigationAdapter adapter;
    private  MenuSelections selections;

    public NavDrawerLoader(Context context, NavigationAdapter adapter) {
        super(context);
        this.adapter = adapter;
    }

    public void initializeVariables() {
        drawerView = getView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) drawerView.findViewById(R.id.drawerLayout);
        drawList = (ListView) drawerView.findViewById(R.id.navList);
    }

    public void setMenuSelections(MenuSelections selections)
    {
        this.selections=selections;
    }

    @Override
    public View getView(int resource) {
        return super.getView(resource);
    }


    @Override
    public void setupComponent() {

        initializeVariables();
        drawList.setAdapter(adapter);
        adapter.populateDrawer();
        drawList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
                mDrawerLayout.closeDrawers();
            }
        });

    }

    // na klik na case1 da se izvesti toj sto e izjavil deka e zainteresiran za selekcijata.
    // od tuka ne znaeme deka postoi druga klasa vo aplikacijata.

    private void selectItemFromDrawer(int position) {
        if (selections == null) {
            return;
        }
        switch (position) {

            case 0:
                selections.onUploadSelected();
                break;
            case 1:
                selections.onMyPostsSelected();
                break;
            case 2:

                selections.onLogoutSelected();
                break;
        }

    }

    public interface MenuSelections{

        void onUploadSelected();
        void onMyPostsSelected();
        void onLogoutSelected();
    }





}
