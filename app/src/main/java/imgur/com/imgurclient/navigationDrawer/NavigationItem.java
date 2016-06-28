package imgur.com.imgurclient.navigationDrawer;

/**
 * Created by Emilija.Pereska on 6/28/2016.
 */
public   class NavigationItem {
    private String title;
    private int image;

    public NavigationItem(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
