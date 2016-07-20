package imgur.com.imgurclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Emilija.Pereska on 7/18/2016.
 */
public abstract class ComponentLoader {

    private Context context;

    public ComponentLoader(Context context) {
        this.context = context;
    }

    protected Context getContext()
    {
        return this.context;
    }

    public  View getView(int resource)
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        return v;
    }
    public abstract void setupComponent();


}
