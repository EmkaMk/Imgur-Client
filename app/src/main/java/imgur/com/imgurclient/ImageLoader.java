package imgur.com.imgurclient;

import java.io.IOException;
import java.util.List;

import imgur.com.imgurclient.models.ImageService.ImageModel;


/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public interface ImageLoader {

    interface Callback2 {

        void onSuccess(List<ImageModel> images) throws IOException;

        void onFailure();
    }

    void load(Callback2 call, int page);

    boolean loadRefreshed(Callback2 call);
}
