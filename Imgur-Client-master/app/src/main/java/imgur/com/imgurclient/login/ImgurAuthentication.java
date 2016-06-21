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
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import imgur.com.imgurclient.Constants;
import imgur.com.imgurclient.ImgurApp;
import imgur.com.imgurclient.ImgurModel;

/**
 * Created by Emilija.Pereska on 6/9/2016.
 */
public class ImgurAuthentication {

    static final String SHARED_PREFERENCES = "shared_prefs";

    private ImgurModel model = new ImgurModel();

    private SharedPreferences getPreferences() {
        Context context = ImgurApp.appContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, 0);
        return prefs;
    }

    private void populateModel(String access_token, String refresh_token, long expiration) {
        model.setAccess_token(access_token);
        model.setRefresh_token(refresh_token);
        model.setExpires_in(expiration);
    }

    private void populatePreferences() {
        SharedPreferences prefs = this.getPreferences();
        prefs.edit()
                .putString("access_token", model.getAccess_token())
                .putString("refresh_token", model.getRefresh_token())
                .putLong("expires_in", model.getExpires_in())
                .commit();
    }

    public boolean isLoggedIn() {
        Context context = ImgurApp.appContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, 0);

        if (TextUtils.isEmpty(prefs.getString("access_token", null)))
            return false;
        return true;

    }

    public void getAccessToken(String access_token, String refresh_token, long expiration) {
        populateModel(access_token, refresh_token, expiration);
        populatePreferences();

    }



    private String getQuery(ContentValues params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : params.valueSet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Log.i(ImgurAuthentication.class.getSimpleName(), entry.getValue().toString());

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

    public void setHeader(HttpURLConnection conn) {
        Context context = ImgurApp.appContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, 0);
        String access_token = prefs.getString("access_token", null);
        if (!TextUtils.isEmpty(access_token)) {
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
        } else
            conn.setRequestProperty("Authorization", "Cliend-ID" + Constants.CLIENT_ID);
    }


    public void logOut() {
        Context context = ImgurApp.appContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, 0);
        prefs.edit().clear().commit();
    }

    public String newAccessToken() {
        Context context = ImgurApp.appContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, 0);
        String access_token = prefs.getString("access_token", null);
        String refresh_token = prefs.getString("refresh_token", null);
        prefs.edit().remove(access_token);

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
        Context context = ImgurApp.appContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, 0);
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

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", access_token);
        editor.putString("refresh_token", refresh_token);
        editor.putString("token_type", token_type);
        editor.putString("account_username", account_username);
        editor.putLong("expires_in", expires_in);
        editor.commit();

    }


}
