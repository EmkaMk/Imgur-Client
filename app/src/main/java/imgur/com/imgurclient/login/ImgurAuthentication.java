package imgur.com.imgurclient.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import imgur.com.imgurclient.ImgurApp;
import imgur.com.imgurclient.models.ImageService.AuthorizationResponse;

/**
 * Created by Emilija.Pereska on six/9/2016.
 */
public class ImgurAuthentication {


    private static final ImgurAuthentication INSTANCE = new ImgurAuthentication();
    private AuthorizationResponse model;

    private ImgurAuthentication() {}

    public static  ImgurAuthentication getInstance()
    {
        return INSTANCE;
    }

    public void setModel(AuthorizationResponse model) {
        this.model = model;
    }

    public void setTokens(String access_token, String refresh_token, long expiration, String token_type, String account_username) {
        this.model.setAccess_token(access_token);
        this.model.setRefresh_token(refresh_token);
        this.model.setExpires_in(expiration);
        this.model.setToken_type(token_type);
        this.model.setAccount_username(account_username);
    }

    public String getAccessToken() {
        return model.getAccess_token();
    }

    public void logOut() {

        model.setAccess_token("");
    }


    public boolean isLoggedIn() {

        return isAccessTokenValid();
    }

    public boolean isAccessTokenValid() {

        final String accessToken = getAccessToken();

        return accessToken != null && (!accessToken.isEmpty());
    }
}




