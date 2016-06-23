package imgur.com.imgurclient;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import imgur.com.imgurclient.models.ImageService.Image;

public class ImagesTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_test);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //ImageView iview= (ImageView) parent.getItemAtPosition(position);


            }
        });

    }


    class ImageAdapter extends BaseAdapter {

        private Context context;
        private Integer[] images = {R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four, R.mipmap.five, R.mipmap.six, R.mipmap.seven
        };

        public ImageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            //return null;
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView view;
            if (convertView == null) {
                view = new ImageView(context);
                view.setLayoutParams(new GridView.LayoutParams(300, 300));
                view.setPadding(8, 8, 8, 8);

            } else {
                view = (ImageView) convertView;
            }
            view.setImageResource(images[position]);
            return view;
        }
    }
}
