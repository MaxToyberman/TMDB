package com.toyberman.tmdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Utils.GetTopResultsAsync;
import tmdb.entities.ApiTopmovies;
import tmdb.entities.Movie;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="TMDB";
    private static final String TOP_RESULTS_URL = "http://api.themoviedb.org/3/discover/movie?api_key=KEY&primary_release_year=2015&sort_by=popularity.desc";

    private  ApiTopmovies apiTopmovies;
    private ListView lv_movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();

    }

    private void initLayout() {
        lv_movies= (ListView) findViewById(R.id.lv_topmovies);
        try {
            apiTopmovies= new GetTopResultsAsync(lv_movies,getLayoutInflater()).execute(TOP_RESULTS_URL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){

            case R.id.main_instructions:
                HelpActivity.saveData("first",false);
                HelpActivity.instructions(this);
                break;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
