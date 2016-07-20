package imgur.com.imgurclient;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.activities.MainActivity;
import imgur.com.imgurclient.models.ImageService.ImageModel;
import imgur.com.imgurclient.models.ImageService.ImageResponse;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public class GetTopPosts implements ImageLoader {

    private List<ImageModel> images = new ArrayList<>();
    private ImgurAPI api;
    boolean needsRefresh = false;

    public GetTopPosts() {
        this.api = ServiceGenerator.createService(ImgurAPI.class);
    }

    @Override
    public void load(final Callback2 callback2, int page) {


        api.getTopPosts(page).enqueue(new Callback<ImgurResponse<List<ImageResponse>>>() {
            @Override
            public void onResponse(Call<ImgurResponse<List<ImageResponse>>> call, Response<ImgurResponse<List<ImageResponse>>> response) {
                if (response.isSuccessful()) {
                    Log.e(GetTopPosts.class.getName(), call.request().toString());

                    images.addAll(getImageAttributes(response));
                    try {
                        callback2.onSuccess(images);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    Log.e(MainActivity.class.getName(), response.message());
            }


            @Override
            public void onFailure(Call<ImgurResponse<List<ImageResponse>>> call, Throwable t) {
                t.printStackTrace();
            }

        });


    }

    @Override
    public boolean loadRefreshed(final Callback2 call2) {
        api.getTopPosts(0).enqueue(new Callback<ImgurResponse<List<ImageResponse>>>() {
            @Override
            public void onResponse(Call<ImgurResponse<List<ImageResponse>>> call, Response<ImgurResponse<List<ImageResponse>>> response) {
                if (response.isSuccessful() && !images.isEmpty()) {
                    if (response.body().data.get(0).equals(images.get(0))) {
                        images.addAll(getImageAttributes(response));
                        try {
                            call2.onSuccess(images);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        needsRefresh = true;

                    } else {
                        needsRefresh = false;
                        call2.onFailure();
                        Log.e(MainActivity.class.getName(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ImgurResponse<List<ImageResponse>>> call, Throwable t) {

                needsRefresh = false;
                t.printStackTrace();

            }
        });
        return needsRefresh;
    }

    protected List<ImageModel> getImageAttributes(Response<ImgurResponse<List<ImageResponse>>> response) {
        ImgurResponse<List<ImageResponse>> imgurResponse = response.body();
        List<ImageModel> imageInfo = new ArrayList<>();

        for (ImageResponse imageResponse : imgurResponse.data) {

            if (imageResponse.getType() != null && !imageResponse.isAnimated()) {
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
        model.setHeight(response.getHeight());
        model.setWidth(response.getWidth());

        return model;
    }
}
