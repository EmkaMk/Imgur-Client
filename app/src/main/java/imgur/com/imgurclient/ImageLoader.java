package imgur.com.imgurclient;

import java.util.List;

import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.models.ImageService.ImageModel;


/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public interface ImageLoader {

    interface Callback2 {
        void onSuccess(List<ImageModel> images);

        void onFailure();
    }

    void load(Callback2 call);
}
