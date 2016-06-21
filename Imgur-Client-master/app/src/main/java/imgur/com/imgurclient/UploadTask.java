package imgur.com.imgurclient;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import imgur.com.imgurclient.login.ImgurAuthentication;

public class UploadTask extends AsyncTask<Void, Void, String> {

    private Uri imageUri;
    private byte[] image;
    private ImgurAuthentication auth=new ImgurAuthentication();
    public UploadTask(Uri uri, byte[] image)
    {
        this.imageUri=uri;
        this.image=image;
    }

    @Override
    protected String doInBackground(Void... params) {
        return null;
    }



    public void upload()
    {
        /*HttpURLConnection connection=null;
        URL url=null;
        try {
            url=new URL(Constants.UPLOAD_URL);
            connection= (HttpURLConnection) url.openConnection();
            auth.setHeader(connection);


            ContentValues values=new ContentValues();
            values.put("image",image);
            values.put("key",Constants.CLIENT_ID);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

}
