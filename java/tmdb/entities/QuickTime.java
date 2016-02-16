package tmdb.entities;

/**
 * Created by owner on 07-Aug-15.
 */
public class QuickTime{

    public String name;
    public String size;
    public String source;
    public String type;
    public static final String QUICK_TIME_URL = "https://www.youtube.com/watch?v=";
    public String getUrl() {
        return QUICK_TIME_URL + source;
    }
}