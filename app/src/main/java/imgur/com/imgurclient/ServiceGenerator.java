package imgur.com.imgurclient;

import java.io.IOException;
import java.util.Random;

import imgur.com.imgurclient.login.ImgurAuthentication;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new RefreshTokenInterceptor())
                            .addInterceptor(new AuthorizationInterceptor())
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    private static class AuthorizationInterceptor implements Interceptor {
        private final ImgurAuthentication auth;

        private AuthorizationInterceptor() {
            auth = new ImgurAuthentication();
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            if (!auth.isLoggedIn()) {
                return chain.proceed(chain.request());
            }
            if (!isAccessTokenValid()) {
                refreshAccessTokenAndBlock();
            }
            Request requestWithAuth = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + auth.getAccessToken())
                    .build();
            return chain.proceed(requestWithAuth);
        }

        private boolean isAccessTokenValid() {
            final String accessToken = auth.getAccessToken();
//            todo
//            if (accessToken == null) {
//                return false;
//            }
//            return !accessToken.isEmpty();
            return accessToken != null && !accessToken.isEmpty();
        }

        private void refreshAccessTokenAndBlock() {
        }
    }

    private static class RefreshTokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            if (!isAccessTokenValid()) {
                refreshAccessToken();
            }
            return chain.proceed(chain.request());
        }

        private boolean isAccessTokenValid() {
            return true;
        }

        private void refreshAccessToken() {
        }
    }
}
