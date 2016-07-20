package imgur.com.imgurclient;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ImgurApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this.getApplicationContext();
        Log.e(ImgurApp.class.getName(),"IM HERE");
    }

    public static Context appContext()
    {
        return context;
    }
}