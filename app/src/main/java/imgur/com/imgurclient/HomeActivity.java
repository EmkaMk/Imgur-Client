package imgur.com.imgurclient;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import imgur.com.imgurclient.login.ImgurAuthentication;
import imgur.com.imgurclient.models.ImageService.AuthorizationResponse;


public class HomeActivity extends AppCompatActivity {

    private static final String URL = "https://api.imgur.com/oauth2/authorize?client_id=" + Constants.CLIENT_ID + "&response_type=token";
    private Button login;
    private ImgurAuthentication auth = ImgurAuthentication.getInstance();
    private AuthorizationResponse model = AuthorizationResponse.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth.setModel(model);
        handleLogin();
    }

    public void handleLogin() {
        if (getIntent().getAction().equals(Intent.ACTION_VIEW)) // uspesno logiranje
        {

            Uri data = getIntent().getData();
            if (data != null && data.toString().contains(Constants.REDIRECT_URL)) {
                this.getURIValues(data);
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();

            } else {
                Toast.makeText(HomeActivity.this, "Login is not successful", Toast.LENGTH_LONG).show();
            }
        } else { // prvoLogiranje
            login = (Button) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(URL));
                    startActivityForResult(browserIntent, 10);
                    finish();
                }
            });
        }
    }


    public void getURIValues(Uri uri) {

        String access_token, refresh_token, token_type, account_username;
        Long expires_in;
        Pattern a_token = Pattern.compile("access_token=([^&]*)");
        Pattern r_token = Pattern.compile("refresh_token=([^&]*)");
        Pattern expires = Pattern.compile("expires_in=([\\d]*)");
        Pattern t_type = Pattern.compile("token_type=([^&]*)");
        Pattern a_username = Pattern.compile("account_username=([^&]*)");
        access_token = matcherValue(a_token, uri.toString());
        refresh_token = matcherValue(r_token, uri.toString());
        expires_in = Long.valueOf(matcherValue(expires, uri.toString()));
        account_username = matcherValue(a_username, uri.toString());
        token_type = matcherValue(t_type, uri.toString());
        auth.setTokens(access_token, refresh_token, expires_in, token_type, account_username);

    }

    public String matcherValue(Pattern p, String uri) {
        Matcher m;
        m = p.matcher(uri);
        m.find();
        return m.group(1);
    }

}
