package imgur.com.imgurclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.login.ImgurAuthentication;
import imgur.com.imgurclient.models.ImageService.Image;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import retrofit2.Call;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    ImageView imageView;
    Button upload, select;
    boolean uploaded = false;
    Uri imageUri;
    ImgurAuthentication authentication;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_chooser);
        authentication = new ImgurAuthentication();
        imageView = (ImageView) findViewById(R.id.view);
        upload = (Button) findViewById(R.id.upload);
        select = (Button) findViewById(R.id.select);
        chooseImage();

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadSelectedImage();

            }
        });

    }

    private void uploadSelectedImage() {

        ImgurAPI api = ServiceGenerator.createService(ImgurAPI.class);
        Log.e(UploadActivity.class.getName(), "Service created");
        Call<ImgurResponse<Image>> call = api.uploadImage(encodedImage());
        Log.e(UploadActivity.class.getName(), "Call created");
        progressDialog = ProgressDialog.show(this, "Uploading image", "Please wait while the image is being uploaded", true);
        call.enqueue(new retrofit2.Callback<ImgurResponse<Image>>() {
            @Override
            public void onResponse(Call<ImgurResponse<Image>> call, Response<ImgurResponse<Image>> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    Toast.makeText(UploadActivity.this, "Successful upload", Toast.LENGTH_LONG).show();
                    setContentView(R.layout.image_chooser);
                    startActivity(new Intent(UploadActivity.this, MainActivity.class).putExtra("MyPosts", "MyPosts"));

                }
            }

            @Override
            public void onFailure(Call<ImgurResponse<Image>> call, Throwable t) {

                Log.i(UploadActivity.class.getName(), "Error in response");
            }
        });
        Log.e(UploadActivity.class.getName(), "Call enqueued");
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
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploaded = true;

        }
    }

    public String encodedImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }


}

