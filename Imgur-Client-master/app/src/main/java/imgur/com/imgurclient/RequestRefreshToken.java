package imgur.com.imgurclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;

import imgur.com.imgurclient.login.ImgurAuthentication;

public class RequestRefreshToken extends AsyncTask<Void, Void, String> {

    ImgurAuthentication auth=new ImgurAuthentication();


    @Override
    protected String doInBackground(Void... params)  {

        return auth.newAccessToken();

    }
}