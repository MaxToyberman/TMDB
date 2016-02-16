package tmdb.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by owner on 07-Aug-15.
 */
public class Backdrop implements Parcelable {

    public double aspect_ratio;
    public String file_path;
    public Double height;
    public Double width;

    protected Backdrop(Parcel in) {
        aspect_ratio = in.readDouble();
        file_path = in.readString();
    }

    public static final Creator<Backdrop> CREATOR = new Creator<Backdrop>() {
        @Override
        public Backdrop createFromParcel(Parcel in) {
            return new Backdrop(in);
        }

        @Override
        public Backdrop[] newArray(int size) {
            return new Backdrop[size];
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
    }
}
