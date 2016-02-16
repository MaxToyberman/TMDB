package tmdb.entities;

import java.util.ArrayList;

/**
 * Created by Toyberman Maxim on 07-Aug-15.
 */
public class Trailers {
    public QuickTime[] quicktime;
    public YouTube[] youtube;

    public ArrayList<String>  getUrls(){

        ArrayList<String> names=new ArrayList<String>();
        for(YouTube y:youtube){
            names.add(y.name);
        }
    return  names;
    }
}

