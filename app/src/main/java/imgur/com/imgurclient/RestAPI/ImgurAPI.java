package imgur.com.imgurclient.RestAPI;

import java.util.List;

import imgur.com.imgurclient.models.ImageService.Image;
import imgur.com.imgurclient.models.ImageService.ImageResponse;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import imgur.com.imgurclient.models.ImageService.UserAccountModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by emilija.pereska on six/20/2016.
 */
public interface ImgurAPI {

    @FormUrlEncoded
    @POST("/3/image")
    Call<ImgurResponse<Image>> uploadImage(
            @Header("Authorization: ") String auth, @Field("image") String image);


    @GET("/3/gallery/hot/viral/{page}.json")
    Call<ImgurResponse<List<ImageResponse>>> getTopPosts(
            @Header("Authorization: ") String auth,@Path("page") int page
    );

    @GET("/3/account/{username}")
    Call<ImgurResponse<UserAccountModel>> getUserInfo(
            @Header("Authorization: ") String auth, @Path("username") String username

    );


    @GET("/3/gallery/{user}/time/0.json")
    Call<ImgurResponse<List<ImageResponse>>> getMyPosts(
            @Header("Authorization: ") String auth, @Path("user") int id
    );


}
