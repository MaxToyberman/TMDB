package com.toyberman.tmdb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.concurrent.ExecutionException;
import Utils.GetMovieDetailsAsync;
import tmdb.entities.Movie;
import tmdb.entities.MovieDetails;

/**
 * Created by Toyberman Maxim on 07-Aug-15.
 */
public class TopMoviesAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private Movie[] movies;
    private LayoutInflater inflater;
    private Context ctx;

    public TopMoviesAdapter(Movie[] movies, LayoutInflater inflater) {
        this.movies = movies;
        this.inflater = inflater;
        this.ctx = inflater.getContext();
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int position) {
        return movies[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_top_movie_item, parent, false);
        }
        //Find view by  id
        TextView title = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView overview = (TextView) convertView.findViewById(R.id.tvDescription);
        //fetch the current movie
        Movie currentMovies = movies[position];
        //append the movie details to the TextViews
        title.setText(currentMovies.title);
        overview.setText(currentMovies.overview);
        ImageView iv_movie = (ImageView) convertView.findViewById(R.id.iv_movie);

        Picasso.with(ctx).load("http://image.tmdb.org/t/p/w300/" + movies[position].poster_path + "/?api_key=b3b1492d3e91e9f9403a2989f3031b0c").into(iv_movie);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context ctx = view.getContext();
        Intent intent = new Intent(ctx, DetailsActivity.class);

        intent.putExtra("MovieID", movies[position].id);
        ctx.startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        MovieDetails movie_details = null;
        //GET MOVIE DETAILS

        try {
            movie_details = new GetMovieDetailsAsync(view.getContext())
                    .execute("https://api.themoviedb.org/3/movie/" + movies[position].id + "?api_key=b3b1492d3e91e9f9403a2989f3031b0c&append_to_response=releases,trailers,alternativetitles,keywords,similarmovies,images,casts").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent poster_intent = new Intent(ctx, PosterActivity.class);
        poster_intent.putExtra("Posters", movie_details.images.posters);
        poster_intent.putExtra("Backdrops", movie_details.images.backdrops);
        ctx.startActivity(poster_intent);

        return true;
    }
}
