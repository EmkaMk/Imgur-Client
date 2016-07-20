package imgur.com.imgurclient.navigationDrawer;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NavDrawerView extends ListView {

    private DrawerLayout drawerLayout;


    public NavDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAdapter(new NavigationAdapter(context));
    }

    public void setDrawerLayout(DrawerLayout drawerLayout)
    {
        this.drawerLayout=drawerLayout;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (!(adapter instanceof NavigationAdapter)) {
            throw new IllegalArgumentException();
        }
        super.setAdapter(adapter);
    }

    public void setMenuSelection(MenuSelection ms) {
        setOnItemClickListener(new ClickForwarder(ms));
       // drawerLayout.closeDrawers();
    }

    public interface MenuSelection {

        void onUploadSelected();

        void onMyPostsSelected();

        void onLogoutSelected();
    }

    private static class ClickForwarder implements OnItemClickListener {
        private MenuSelection listener;

        ClickForwarder(MenuSelection listener) {
            this.listener = listener;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                listener.onUploadSelected();
            } else if (position == 1) {
                listener.onMyPostsSelected();
            } else if (position == 2) {
                listener.onLogoutSelected();
            }

        }
    }
}
