package imgur.com.imgurclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ShowImageActivity extends AppCompatActivity {

    ImageView image;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        image=(ImageView) findViewById(R.id.imageView);
        title= (TextView) findViewById(R.id.title);
        Picasso.with(this).load(getIntent().getStringExtra("imageLink")).into(image);
        title.setText(getIntent().getStringExtra("title"));

    }
}
