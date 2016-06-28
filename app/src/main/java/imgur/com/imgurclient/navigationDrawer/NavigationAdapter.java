package imgur.com.imgurclient.navigationDrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import imgur.com.imgurclient.R;

/**
 * Created by Emilija.Pereska on 6/28/2016.
 */
public class NavigationAdapter extends BaseAdapter {
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
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, null);
        } else {
            view = convertView;
        }

        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView image = (ImageView) view.findViewById(R.id.icon);

        title.setText(items.get(position).getTitle());
        image.setImageResource(items.get(position).getImage());
        return view;
    }
}