package imgur.com.imgurclient.models.ImageService;

/**
 * Created by Emilija.Pereska on 6/27/2016.
 */
public class ImageResponse {

    private String id;
    private String title;
    private String description;
    private long datetime;
    private String type;
    private boolean animated;
    private int width;
    private int height;
    private int size;
    private int views;
    private long bandwidth;
    private String deletehash;
    private String name;
    private String section;
    private String link;
    private String gifv;
    private String mp4;
    private int mp4_size;
    private boolean looping;
    private boolean favorite;
    private boolean nsfw;
    private String vote;
    private boolean in_gallery;

    public String getLink() {
        return link;
    }

    public String getType() {
        return type;
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

    public String getId()
    {
        return id;
    }

    public int getViews()
    {
        return views;
    }

}
