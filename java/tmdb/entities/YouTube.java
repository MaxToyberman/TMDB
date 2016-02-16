package tmdb.entities;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Toyberman Maxim on 07-Aug-15.
 */
public class YouTube implements Parcelable {

    public String name;
    public String size;

    public String getSource() {
        return source;
    }

    public String source;
    public String type;
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    protected YouTube(Parcel in) {
        name = in.readString();
        size = in.readString();
        source = in.readString();
        type = in.readString();
    }

    public static final Creator<YouTube> CREATOR = new Creator<YouTube>() {
        @Override
        public YouTube createFromParcel(Parcel in) {
            return new YouTube(in);
        }

        @Override
        public YouTube[] newArray(int size) {
            return new YouTube[size];
        }
    };

    public String getUrl() {
        return YOUTUBE_URL + source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(source);
        dest.writeString(type);
    }
}
