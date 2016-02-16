package Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.toyberman.tmdb.DetailsActivity;
import com.toyberman.tmdb.R;
import com.toyberman.tmdb.TopMoviesAdapter;

import java.io.InputStream;
import java.io.InputStreamReader;

import tmdb.entities.ApiTopmovies;
import tmdb.entities.MovieDetails;

/**
 * Created by Toyberman Maxim on 09-Aug-15.
 */
public class GetMovieDetailsAsync extends AsyncTask<String, Integer, MovieDetails> {


    private TextView tv_movie_name;
    private TextView tv_summary;
    private ImageView imageView;
    private FloatingActionButton btn_trailer;
    private RatingBar ratingBar;
    private ProgressBar progressBar;
    private Context ctx;
    private Boolean isPosterActivity = false;


    public GetMovieDetailsAsync(TextView tv_movie_name, TextView tv_summary, ImageView imageView, FloatingActionButton btn_trailer, RatingBar ratingBar, ProgressBar progressBar, Context ctx) {
        this.tv_movie_name = tv_movie_name;
        this.tv_summary = tv_summary;
        this.imageView = imageView;
        this.btn_trailer = btn_trailer;
        this.ratingBar = ratingBar;
        this.progressBar = progressBar;
        this.ctx = ctx;
    }

    public GetMovieDetailsAsync(Context ctx) {
        this.ctx = ctx;
        isPosterActivity = true;

    }

    @Override
    protected MovieDetails doInBackground(String... params) {

        InputStream in = HttpHandler.getUrlData((params[0]));
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(in);
        MovieDetails movieDetails = gson.fromJson(reader, MovieDetails.class);

        return movieDetails;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //SHOW PROGRESS BAR
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(MovieDetails movieDetails) {
        super.onPostExecute(movieDetails);
        Log.d("TMDB", "here");
        if (isPosterActivity) {
            isPosterActivity = false;
            return;
        }

        //setting the movie name
        tv_movie_name.setText(movieDetails.title);
        //setting the rating
        ratingBar.setRating(Float.parseFloat(String.valueOf(movieDetails.vote_average)));
        //setting the image
        Picasso.with(ctx).load("http://image.tmdb.org/t/p/w500/" + movieDetails.poster_path + "/?api_key=b3b1492d3e91e9f9403a2989f3031b0c").placeholder(R.drawable.place_holder).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        //setting the summary
        tv_summary.setText(movieDetails.overview);
        //adding scroll to the summary textview
        tv_summary.setMovementMethod(new ScrollingMovementMethod());
        //init Events
    }

}
