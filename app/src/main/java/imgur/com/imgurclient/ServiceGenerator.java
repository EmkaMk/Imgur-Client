package imgur.com.imgurclient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emilija.pereska on six/20/2016.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "https://api.imgur.com";

    private static Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
