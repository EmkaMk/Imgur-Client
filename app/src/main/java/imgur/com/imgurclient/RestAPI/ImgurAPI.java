package imgur.com.imgurclient.RestAPI;

import java.util.List;

import imgur.com.imgurclient.models.ImageService.Image;
import imgur.com.imgurclient.models.ImageService.ImageResponse;
import imgur.com.imgurclient.models.ImageService.ImgurResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by emilija.pereska on six/20/2016.
 */
public interface ImgurAPI {

    @FormUrlEncoded
    @POST("/3/image")
    Call<ImgurResponse<Image>> uploadImage(
            @Header("Authorization: ") String auth, @Field("image") String image);


    @GET("/3/gallery/hot/viral/0.json")
    //@GET("/3/gallery/hot/viral/1?showViral=bool")

    Call<ImgurResponse<List<ImageResponse>>> getImages(
            @Header("Authorization: ") String auth
    );


}