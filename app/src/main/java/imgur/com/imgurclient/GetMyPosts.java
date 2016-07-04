package imgur.com.imgurclient;

import java.util.List;

import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.login.ImgurAuthentication;
import imgur.com.imgurclient.models.ImageService.ImageResponse;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public class GetMyPosts implements ImageLoader {

    private ImgurAPI api;

    public void GetMyPosts(ImgurAPI api) {
        this.api = api;
    }

    @Override
    public void load(Callback2 call) {

        api.getMyPosts(0).enqueue(new Callback<ImgurResponse<List<ImageResponse>>>() {
            @Override
            public void onResponse(Call<ImgurResponse<List<ImageResponse>>> call, Response<ImgurResponse<List<ImageResponse>>> response) {

            }

            @Override
            public void onFailure(Call<ImgurResponse<List<ImageResponse>>> call, Throwable t) {

            }
        });

    }
}
