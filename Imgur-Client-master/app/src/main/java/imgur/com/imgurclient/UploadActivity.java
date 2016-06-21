package imgur.com.imgurclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UploadActivity extends AppCompatActivity {

     ImageView imageView;
    Button upload,select;
    boolean uploaded=false;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_chooser);
        imageView = (ImageView) findViewById(R.id.view);
        upload= (Button) findViewById(R.id.upload);
        select= (Button) findViewById(R.id.select);
        chooseImage();

    }

    private void chooseImage() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
              imageUri=data.getData();
            imageView.setImageURI(imageUri);
            uploaded=true;

        }
    }

    protected void upload() throws IOException {
        UploadTask upload;
        if(!uploaded)
        {
            Toast.makeText(UploadActivity.this,"Image not selected",Toast.LENGTH_LONG).show();
        }
        else
        {
            upload=new UploadTask(imageUri,convertImage());
            upload.execute();
        }
    }

    public byte[] convertImage() throws IOException {
        InputStream iStream =  getContentResolver().openInputStream(imageUri);

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = iStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();

    }





}

