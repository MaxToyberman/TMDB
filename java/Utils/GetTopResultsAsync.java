package Utils;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.ListView;
import com.google.gson.Gson;
import com.toyberman.tmdb.TopMoviesAdapter;
import java.io.InputStream;
import java.io.InputStreamReader;
import tmdb.entities.ApiTopmovies;


/**
 * Created by owner on 07-Aug-15.
 */
public class GetTopResultsAsync extends AsyncTask<String,Integer,ApiTopmovies> {

    private final ListView lv_movies;
    private final LayoutInflater inflater;

    public GetTopResultsAsync(ListView lv_movies,LayoutInflater inflater) {
        this.lv_movies=lv_movies;
        this.inflater=inflater;

    }

    @Override
    protected ApiTopmovies doInBackground(String... params)
    {
        InputStream in = HttpHandler.getUrlData((params[0]));
        Gson gson = new Gson();
        InputStreamReader reader=new InputStreamReader(in);
        ApiTopmovies apiTopMovies= gson.fromJson(reader, ApiTopmovies.class);

        return apiTopMovies;
    }


    @Override
    protected void onPostExecute(ApiTopmovies apiTopmovies) {
        super.onPostExecute(apiTopmovies);
        TopMoviesAdapter adapter=new TopMoviesAdapter(apiTopmovies.results,inflater);
        lv_movies.setAdapter(adapter);
        lv_movies.setOnItemClickListener(adapter);
        lv_movies.setOnItemLongClickListener(adapter);
    }
}
