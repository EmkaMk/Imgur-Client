package imgur.com.imgurclient;

import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import imgur.com.imgurclient.models.ImageService.UserModel;
import imgur.com.imgurclient.models.ImageService.UserResponse;
import retrofit2.Call;

/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public interface UserLoader {

    UserModel getUserInfo(Call<ImgurResponse<UserResponse>> call);


}
