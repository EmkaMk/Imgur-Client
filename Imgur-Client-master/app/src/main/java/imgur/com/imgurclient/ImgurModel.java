package imgur.com.imgurclient;

/**
 * Created by Emilija.Pereska on 6/16/2016.
 */
public class ImgurModel {

    private String access_token;
    private String refresh_token;
    private Long expires_in;
    private String token_type;
    private String account_username;

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setAccount_username(String account_username) {
        this.account_username = account_username;
    }

    public String getToken_type() {

        return token_type;
    }

    public String getAccount_username() {
        return account_username;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

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
