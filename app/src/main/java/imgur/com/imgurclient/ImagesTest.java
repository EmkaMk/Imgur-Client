package imgur.com.imgurclient;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import imgur.com.imgurclient.models.ImageService.Image;

public class ImagesTest extends MainActivity {

    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_view);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        //image= (ImageView) findViewById(R.id.image);
        RecyclerView.LayoutManager manager;
        if (getResources().getConfiguration().orientation == 1) {
            manager = new GridLayoutManager(ImagesTest.this, 2);
        } else {
            manager = new GridLayoutManager(ImagesTest.this, 3);
        }
        rView.setLayoutManager(manager);
        rView.setAdapter(new ImageAdapter(this));


    }

}
