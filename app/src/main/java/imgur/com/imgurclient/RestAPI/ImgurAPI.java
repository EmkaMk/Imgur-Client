package imgur.com.imgurclient.RestAPI;

import imgur.com.imgurclient.models.ImageService.Image;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by emilija.pereska on 6/20/2016.
 */
public interface ImgurAPI {

   String IMGUR_URL="https://api.imgur.com";

    @FormUrlEncoded
   @POST("/3/image")
    Call<ImgurResponse<Image>> uploadImage(
           @Header("Authorization") String auth, @Field("image") String image);
           /*@POST("/3/image")
           Call<String> createUser(@Body User user);

           );*/


}
