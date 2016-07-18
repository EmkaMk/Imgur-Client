package imgur.com.imgurclient.models.ImageService;


/**
 * Created by Emilija.Pereska on six/16/2016.
 */
public class AuthorizationResponse {
    private static final AuthorizationResponse INSTANCE = new AuthorizationResponse();

    private String access_token;
    private String refresh_token;
    private Long expires_in;
    private String token_type;
    private String account_username;


    private AuthorizationResponse() {
    }

    public static AuthorizationResponse getInstance() {
        return INSTANCE;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setAccount_username(String account_username) {
        this.account_username = account_username;
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

    public String getAccess_token() {
        return access_token;
    }

}
