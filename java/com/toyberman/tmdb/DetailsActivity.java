package com.toyberman.tmdb;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import Utils.GetMovieDetailsAsync;
import tmdb.entities.MovieDetails;

public class DetailsActivity extends AppCompatActivity {


    private TextView tv_movie_name;
    private TextView tv_summary;
    private ImageView imageView;
    private FloatingActionButton btn_trailer;
    private RatingBar ratingBar;
    private ProgressBar progressBar;
    private MovieDetails movie_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //INIT LAYOUT
        initLayout();
        //GET MOVIE ID
        Intent intent = getIntent();
        int movie_id = intent.getIntExtra("MovieID", -1);
        //GET MOVIE DETAILS
        try {
            movie_details = new GetMovieDetailsAsync(tv_movie_name, tv_summary, imageView, btn_trailer, ratingBar, progressBar, this)
                    .execute("https://api.themoviedb.org/3/movie/" + movie_id + "?api_key=b3b1492d3e91e9f9403a2989f3031b0c&append_to_response=releases,trailers,alternativetitles,keywords,similarmovies,images,casts").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        initEvents();

    }

    private void initEvents() {

        btn_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trailer_intent = new Intent(getApplicationContext(), TrailersActivity.class);
                trailer_intent.putExtra("Trailers", movie_details.trailers.youtube);
                startActivity(trailer_intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent poster_intent = new Intent(getApplicationContext(), PosterActivity.class);
                poster_intent.putExtra("Posters", movie_details.images.posters);
                poster_intent.putExtra("Backdrops", movie_details.images.backdrops);
                startActivity(poster_intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.details_instructions:
                HelpActivity.saveData("first", false);
                HelpActivity.instructions(this);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initLayout() {

        tv_movie_name = (TextView) findViewById(R.id.tv_movie_name);
        tv_summary = (TextView) findViewById(R.id.tv_summary);
        imageView = (ImageView) findViewById(R.id.imageView);
        btn_trailer = (FloatingActionButton) findViewById(R.id.btn_trailer);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
}
