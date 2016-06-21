package imgur.com.imgurclient.models.ImageService;

/**
 * Created by emilija.pereska on 6/20/2016.
 */
public class ImgurResponse<T> {

    public final T data;

    public ImgurResponse(T data) {
        this.data = data;
    }
}
