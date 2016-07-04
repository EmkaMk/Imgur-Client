package imgur.com.imgurclient.models.ImageService;

/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public class UserModel {

    private int id;
    private String url;
    private String bio;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getBio() {
        return bio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
