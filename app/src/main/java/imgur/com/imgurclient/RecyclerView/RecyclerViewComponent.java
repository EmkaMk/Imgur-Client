package imgur.com.imgurclient.RecyclerView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import imgur.com.imgurclient.ImageAdapter;

/**
 * Created by emilija.pereska on 7/20/2016.
 */
public class RecyclerViewComponent extends RecyclerView {

    public RecyclerViewComponent(Context context) {
        super(context);
    }

    public RecyclerViewComponent(Context c,ImageAdapter adapter,GridLayoutManager manager)
    {
        super(c);
        setAdapter(adapter);
        setLayoutManager(manager);
    }
    @Override
    public void setAdapter(Adapter adapter) {

        if (!(adapter instanceof ImageAdapter)) {
            throw new IllegalArgumentException("ImageAdapter is expected");
        }
        super.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {

        if (!(layout instanceof GridLayoutManager)) {
            throw new IllegalArgumentException("GridLayoutManager is expected");
        }
        super.setLayoutManager(layout);
    }
}
