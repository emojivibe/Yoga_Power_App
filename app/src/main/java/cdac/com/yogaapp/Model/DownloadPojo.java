package cdac.com.yogaapp.Model;

/**
 * Created by vinod on 4/27/2019.
 */

public class DownloadPojo {

    private String name;
    private String url;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
}
