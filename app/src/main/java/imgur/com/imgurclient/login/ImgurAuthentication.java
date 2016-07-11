package imgur.com.imgurclient.login;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;

import imgur.com.imgurclient.Constants;
import imgur.com.imgurclient.ImgurApp;
import imgur.com.imgurclient.LoginModel;

/**
 * Created by Emilija.Pereska on six/9/2016.
 */
public class ImgurAuthentication {

    static final String SHARED_PREFERENCES = "shared_prefs";
    LoginModel model = new LoginModel();


    private SharedPreferences getPreferences() {
        Context context = ImgurApp.appContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, 0);
        return prefs;
    }

    private void populateModel(String access_token, String refresh_token, long expiration, String token_type, String account_username) {
        model.setAccess_token(access_token);
        model.setRefresh_token(refresh_token);
        model.setExpires_in(expiration);
        model.setToken_type(token_type);
        model.setAccount_username(account_username);
    }

    private void populatePreferences() {
        getPreferences().edit()
                .putString("access_token", LoginModel.access_token)
                .putString("refresh_token", LoginModel.refresh_token)
                .putLong("expires_in", LoginModel.expires_in)
                .putString("token_type", LoginModel.token_type)
                .putString("account_username", LoginModel.account_username)
                .commit();
    }

    public boolean isLoggedIn() {

        if (TextUtils.isEmpty(getPreferences().getString("access_token", null)))
            return false;
        return true;

    }

    public void setTokens(String access_token, String refresh_token, long expiration, String token_type, String account_username) {
        populateModel(access_token, refresh_token, expiration, token_type, account_username);
        populatePreferences();
    }

    public String getAccessToken() {
        return getPreferences().getString("access_token", null);
    }

    public void logOut() {
        getPreferences().edit().clear().commit();
    }
}
