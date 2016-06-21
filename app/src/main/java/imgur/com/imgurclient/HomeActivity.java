package imgur.com.imgurclient;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import imgur.com.imgurclient.login.ImgurAuthentication;


public class HomeActivity extends AppCompatActivity {

    private static final String URL = "https://api.imgur.com/oauth2/authorize?client_id=" + Constants.CLIENT_ID + "&response_type=token";

    Button login, logout, upload;
    ImgurAuthentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = new ImgurAuthentication();


        if (auth.isLoggedIn()) {
            setContentView(R.layout.activity_main);

        } else {
            login = (Button) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(URL));
                    startActivityForResult(browserIntent, 10);
                }
            });
        }


        if (getIntent().getAction().equals(Intent.ACTION_VIEW)) // uspesno logiranje
        {

            Uri data = getIntent().getData();
            if (data != null && data.toString().contains(Constants.REDIRECT_URL)) {
                this.getURIValues(data, auth);
                setContentView(R.layout.activity_main);
                Toast.makeText(HomeActivity.this, "Logged in!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(HomeActivity.this, "null", Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new RequestRefreshToken().execute();
        //auth.newAccessToken();
        if (auth.isLoggedIn()) {
            setContentView(R.layout.activity_home);
            upload();
            logOut();
        } else {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(URL));
                    startActivityForResult(browserIntent, 10);
                }
            });
        }
    }

    public void upload() {
        upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(HomeActivity.this, UploadActivity.class);
                startActivity(upload);
            }
        });
    }

    public void logOut() {
        logout = (Button) findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.logOut();
                setContentView(R.layout.activity_main);
            }
        });
    }

    public void getURIValues(Uri uri, ImgurAuthentication auth) {
        Matcher m;
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
