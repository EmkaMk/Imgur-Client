package imgur.com.imgurclient;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ShowImageActivity extends AppCompatActivity {

    ImageView image;
    TextView title;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        image= (ImageView) findViewById(R.id.imageView);
       // title= (TextView) findViewById(R.id.title);

        //title.setText(getIntent().getStringExtra("title"));
        link=getIntent().getStringExtra("imageLink");

       /* AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog dialog=builder.create();
        LayoutInflater inflater=getLayoutInflater();
        View dialogLayout=inflater.inflate(R.layout.activity_show_image, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {


                Picasso.with(getApplicationContext()).load(link).into(image);

            }
        });
        dialog.show();*/


    }
}
