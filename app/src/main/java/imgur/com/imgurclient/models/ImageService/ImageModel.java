package imgur.com.imgurclient.models.ImageService;

/**
 * Created by Emilija.Pereska on 7/4/2016.
 */
public class ImageModel {

    private String id;
    private String title;
    private String description;
    private boolean animated;
    private int views;
    private String vote;
    private String type;
    private String link;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAnimated() {
        return animated;
    }

    public int getViews() {
        return views;
    }

    public String getVote() {
        return vote;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {

        return link;
    }

}
