package imgur.com.imgurclient.RestAPI;

import java.util.List;

import imgur.com.imgurclient.models.ImageService.Image;
import imgur.com.imgurclient.models.ImageService.ImageResponse;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import imgur.com.imgurclient.models.ImageService.UserResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by emilija.pereska on six/20/2016.
 */
public interface ImgurAPI {

    @FormUrlEncoded
    @POST("image")
    Call<ImgurResponse<Image>> uploadImage(
            @Field("image") String image);


    @GET("gallery/hot/viral/{page}.json")
    Call<ImgurResponse<List<ImageResponse>>> getTopPosts(
            @Path("page") int page
    );

    @GET("account/{username}")
    Call<ImgurResponse<UserResponse>> getUserInfo(
            @Path("username") String username

    );

    @GET("account/me/images")
    Call<ImgurResponse<List<ImageResponse>>> getMyPosts(
    );


}
