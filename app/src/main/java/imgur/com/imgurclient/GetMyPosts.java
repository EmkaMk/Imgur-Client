package imgur.com.imgurclient;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.models.ImageService.ImageModel;
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
    private List<ImageModel> images = new ArrayList<>();

    public void GetMyPosts() {
        this.api = ServiceGenerator.createService(ImgurAPI.class);
    }

    @Override
    public void load(final Callback2 callback2) {

        api.getMyPosts("EmkaMK").enqueue(new Callback<ImgurResponse<List<ImageResponse>>>() {
            @Override
            public void onResponse(Call<ImgurResponse<List<ImageResponse>>> call, Response<ImgurResponse<List<ImageResponse>>> response) {
                if (response.isSuccessful()) {
                    Log.e(GetMyPosts.class.getName(), "Request is successfull");
                    images = getImageAttributes(response);
                    callback2.onSuccess(images);
                } else {
                    Log.e(MainActivity.class.getName(), response.message());
                }
            }

            @Override
            public void onFailure(Call<ImgurResponse<List<ImageResponse>>> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    protected List<ImageModel> getImageAttributes(Response<ImgurResponse<List<ImageResponse>>> response) {
        ImgurResponse<List<ImageResponse>> imgurResponse = response.body();
        List<ImageModel> imageInfo = new ArrayList<>();

        for (ImageResponse imageResponse : imgurResponse.data) {

            if (imageResponse.getType() != null && imageResponse.isAnimated()) {
                ImageModel model = this.getImageAttributes(imageResponse);
                imageInfo.add(0, model);

            }
        }

        return imageInfo;

    }


    private ImageModel getImageAttributes(ImageResponse response) {

        ImageModel model = new ImageModel();
        model.setId(response.getId());
        model.setTitle(response.getTitle());
        model.setDescription(response.getDescription());
        model.setType(response.getType());
        model.setViews(response.getViews());
        model.setAnimated(response.isAnimated());
        model.setLink(response.getLink());

        return model;
    }
}
