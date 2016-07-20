package imgur.com.imgurclient.navigationDrawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

import imgur.com.imgurclient.navigationDrawer.NavigationAdapter;


/**
 * Created by Emilija.Pereska on 7/19/2016.
 */
public class NavDrawerView extends ListView {

    private MenuSelection ms;

    public NavDrawerView(Context context) {
        this(context, null);
    }

    public NavDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAdapter(new NavigationAdapter(context));
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (!(adapter instanceof NavigationAdapter)) {
            throw new IllegalArgumentException();
        }
        super.setAdapter(adapter);
        ((NavigationAdapter) adapter).populateDrawer();
    }

    public void setMs(MenuSelection ms) {

        this.ms=ms;
    }

    public void selectItem()
    {
        ms.onSelectItem();
    }

    public interface MenuSelection {

        void onSelectItem();
    }

}
