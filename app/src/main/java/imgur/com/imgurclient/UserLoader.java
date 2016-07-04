package imgur.com.imgurclient;

import imgur.com.imgurclient.models.ImageService.UserModel;


/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public interface UserLoader {


    interface Callback2
    {
        void onSuccess(UserModel model);

        void onFailure();
    }

    void load(Callback2 callback);


}
