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


    private String getQuery(ContentValues params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : params.valueSet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Log.i(ImgurAuthentication.class.getSimpleName(), String.valueOf(entry.getValue()));

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
        }
        return result.toString();
    }

    public String getAccessToken() {
        return getPreferences().getString("access_token", null);
    }

    public String getUsername() {
        return getPreferences().getString("account_username", null);
    }

    public void logOut() {
        getPreferences().edit().clear().commit();
    }

    public String newAccessToken() {
        Context context = ImgurApp.appContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, 0);
        String refresh_token = prefs.getString("refresh_token", null);

        HttpURLConnection connection = null;
        URL url = null;
        try {
            url = new URL("https://api.imgur.com/oauth2/token");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Authorization", "Client-ID " + Constants.CLIENT_ID);

            ContentValues values = new ContentValues();
            values.put("refresh_token", refresh_token);
            values.put("client_id", Constants.CLIENT_ID);
            values.put("client_secret", Constants.CLIENT_SECRET);
            values.put("grant_type", refresh_token);

            String query = this.getQuery(values);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream stream = connection.getInputStream();
                this.handleJSONResponse(stream);
                stream.close();
            }
            return prefs.getString("access_token", null);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            connection.disconnect();
        }
    }


    public void handleJSONResponse(InputStream inputStream) throws JSONException {
        String access_token, refresh_token, token_type, account_username;
        long expires_in;
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            sb.append(scanner.next());
        }

        JSONObject json = new JSONObject(sb.toString());
        access_token = json.getString("access_token");
        refresh_token = json.getString("refresh_token");
        token_type = json.getString("token_type");
        account_username = json.getString("account_username");
        expires_in = json.getLong("expires_in");

        setTokens(access_token, refresh_token, expires_in, token_type, account_username);

    }


}
