package imgur.com.imgurclient;

/**
 * Created by emilija.pereska on 6/24/2016.
 */
public
class Post {
    String title;
    String description;
    int datetime;
    int views;
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image)
    {
        this.image=image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDatetime() {
        return datetime;
    }

    public int getViews() {
        return views;
    }
}