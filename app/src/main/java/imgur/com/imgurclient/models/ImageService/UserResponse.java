package imgur.com.imgurclient.models.ImageService;

/**
 * Created by Emilija.Pereska on 6/30/2016.
 */
public class UserResponse {

    private int id;
    private String url;
    private String bio;
    private float reputation;
    private int created;
    private boolean pro_expiration;

   /* public String getUrl()
    {
        return url;
    }*/

    public int getCreated()
    {
        return created;
    }

    public int getId()
    {
        return id;
    }

    public String getBio(){
        return bio;
    }
}
