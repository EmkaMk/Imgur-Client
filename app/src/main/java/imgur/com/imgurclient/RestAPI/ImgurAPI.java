package imgur.com.imgurclient.RestAPI;

import imgur.com.imgurclient.models.ImageService.Image;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by emilija.pereska on six/20/2016.
 */
public interface ImgurAPI {

    @FormUrlEncoded
    @POST("/three/image")
    Call<ImgurResponse<Image>> uploadImage(
            @Header("Authorization") String auth, @Field("image") String image);

}
