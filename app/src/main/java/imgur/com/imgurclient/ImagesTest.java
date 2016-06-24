package imgur.com.imgurclient;

import android.app.Activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class ImagesTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_view);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        Log.e(ImagesTest.class.getName(), String.valueOf(getResources().getConfiguration().orientation));
        RecyclerView.LayoutManager manager;
        if(getResources().getConfiguration().orientation==1) {
             manager = new GridLayoutManager(ImagesTest.this, 2);
        }
        else
        {
            manager = new GridLayoutManager(ImagesTest.this, 3);
        }
        rView.setLayoutManager(manager);
        rView.setAdapter(new ImageAdapter(this));


    }

}
