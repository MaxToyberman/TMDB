package Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageSwitcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Toyberman Maxim on 22-Jul-15.
 */
public class HttpHandler {
    /*
        This class handles http connections

     */

    public static InputStream getUrlData(String urlString){
        InputStream inputStream=null;
        try {
            //obtaining languages for spinners
            URL url=new URL(urlString);
            inputStream =url.openStream();


        } catch (IOException e) {
            Log.d("HttpHandler",e.getMessage());
        }

        return inputStream;
    }
    public static Bitmap fetchImage(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream inputStream = con.getInputStream();

        return BitmapFactory.decodeStream(inputStream);
    }

}
