package imgur.com.imgurclient;

import android.util.Log;

import imgur.com.imgurclient.RestAPI.ImgurAPI;
import imgur.com.imgurclient.activities.MainActivity;
import imgur.com.imgurclient.login.ImgurAuthentication;
import imgur.com.imgurclient.models.ImageService.AuthorizationResponse;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import imgur.com.imgurclient.models.ImageService.UserModel;
import imgur.com.imgurclient.models.ImageService.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public class GetUserInfo implements UserLoader {

    private ImgurAPI api;
    UserModel userModel = new UserModel();
    private ImgurAuthentication authentication;

    public GetUserInfo(ImgurAPI api) {
        this.api = api;
        authentication = ImgurAuthentication.getInstance();
    }

    @Override
    public void load(final Callback2 callback) {

        api.getUserInfo(AuthorizationResponse.getInstance().getAccount_username()).enqueue(new Callback<ImgurResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ImgurResponse<UserResponse>> call, Response<ImgurResponse<UserResponse>> response) {

                if (response.isSuccessful()) {

                    //userModel = saveUserInfo(response.body());
                    callback.onSuccess(userModel);
                } else {
                    Log.e(MainActivity.class.getName(), response.message());
                }

            }

            @Override
            public void onFailure(Call<ImgurResponse<UserResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

   /* private UserModel saveUserInfo(ImgurResponse<UserResponse> response) {
        UserModel userModel = new UserModel();
        userModel.setId(response.data.getId());
        userModel.setBio(response.data.getBio());
        userModel.setUrl(response.data.getUrl());
        return userModel;
    }*/
}
