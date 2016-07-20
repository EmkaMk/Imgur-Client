package imgur.com.imgurclient.navigationDrawer;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import imgur.com.imgurclient.R;
import imgur.com.imgurclient.login.ImgurAuthentication;
import imgur.com.imgurclient.models.ImageService.AuthorizationResponse;

/**
 * Created by Emilija.Pereska on 6/28/2016.
 */
public class NavigationAdapter extends BaseAdapter {

    private Context context;
    private List<NavigationItem> items = new ArrayList<>();

    public NavigationAdapter(Context context) {
        this.context = context;
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public void populateDrawer() {

        items.add(new NavigationItem("Upload new photo", R.mipmap.upload));
        items.add(new NavigationItem("My posts", R.mipmap.myposts1));
        items.add(new NavigationItem("Log out", R.mipmap.logout));

    }


}