package com.toyberman.tmdb;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AnimationActivity extends AppCompatActivity {

    private TextView tv_best_movies;
    private TextView tv_by;
    private ImageView iv_stars;
    private ImageView iv_movie_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        //find stars image view
        iv_stars = (ImageView) findViewById(R.id.iv_stars);
        //find the movie "cut" image view
        iv_movie_animation = (ImageView) findViewById(R.id.iv_movie_animation);
        //finding textview best movies
        tv_best_movies = (TextView) findViewById(R.id.tv_best_movies);
        //finding by text view
        tv_by = (TextView) findViewById(R.id.tv_by);
        //setting new font
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/alex.ttf");
        tv_best_movies.setTextSize(50.f);
        tv_best_movies.setTypeface(type);

        tv_by.setTextSize(25.f);
        tv_by.setTypeface(type);

        //animate stars
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        iv_stars.startAnimation(rotate);
        //anumate movie image
        Animation slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        iv_movie_animation.startAnimation(slide_in_left);
        //setting listener to stars Animation

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent main_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main_intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.anim_instructions:
                HelpActivity.saveData("first", false);
                HelpActivity.instructions(this);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
