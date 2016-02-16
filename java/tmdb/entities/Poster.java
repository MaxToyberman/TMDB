package tmdb.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Toyberman Maxim on 07-Aug-15.
 */
public class Poster implements Parcelable {

    public double aspect_ratio;
    public String file_path;
    public double height;
    public double width;

    protected Poster(Parcel in) {
        aspect_ratio = in.readDouble();
        file_path = in.readString();
        height = in.readDouble();
        width = in.readDouble();
    }

    public static final Creator<Poster> CREATOR = new Creator<Poster>() {
        @Override
        public Poster createFromParcel(Parcel in) {
            return new Poster(in);
        }

        @Override
        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(aspect_ratio);
        dest.writeString(file_path);
        dest.writeDouble(height);
        dest.writeDouble(width);
    }
}
