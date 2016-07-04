package imgur.com.imgurclient;

import android.util.Log;

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
    @Override
    public UserModel getUserInfo(Call<ImgurResponse<UserResponse>> call) {

        final UserModel userModel;
        call.enqueue(new Callback<ImgurResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ImgurResponse<UserResponse>> call, Response<ImgurResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    //userModel=getAttributes(response.body());
                    Log.e(MainActivity.class.getName(), "Response is successful");
                } else
                    Log.e(MainActivity.class.getName(), "Response not successful");
            }

            @Override
            public void onFailure(Call<ImgurResponse<UserResponse>> call, Throwable t) {

                Log.e(MainActivity.class.getName(), "Failure");
                t.printStackTrace();

            }
        });
        return new UserModel();
    }

    public UserModel getAttributes(ImgurResponse<UserResponse> response)
    {

        UserModel model=new UserModel();
        model.setBio(response.data.getBio());
        model.setId(response.data.getId());
        model.setUrl(response.data.getUrl());
        return model;

    }
}
