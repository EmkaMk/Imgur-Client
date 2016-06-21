package imgur.com.imgurclient;

/**
 * Created by Emilija.Pereska on 6/16/2016.
 */
public class ImgurModel {

    public static String access_token;
    public static String refresh_token;
    public static Long expires_in;
    public static String token_type;
    public static String account_username;

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setAccount_username(String account_username) {
        this.account_username = account_username;}

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }
}
