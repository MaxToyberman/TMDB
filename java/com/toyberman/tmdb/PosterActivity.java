package com.toyberman.tmdb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.IOException;
import Utils.HttpHandler;
import tmdb.entities.Backdrop;
import tmdb.entities.Poster;

public class PosterActivity extends AppCompatActivity {

    private Parcelable[] posters;
    private Parcelable[] backdrops;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        //get posters of movie
        Intent intent = getIntent();
        posters = intent.getParcelableArrayExtra("Posters");
        backdrops = intent.getParcelableArrayExtra("Backdrops");
        viewPager = (ViewPager) findViewById(R.id.pager);
        try {
            ImagePagerAdapter adapter = new ImagePagerAdapter("Posters", posters);
            viewPager.setAdapter(adapter);
        } catch (Exception e) {
            Log.d("TMDB", e.getMessage());
        }

    }

    private class ImagePagerAdapter extends PagerAdapter {

        public ImagePagerAdapter(String type, Parcelable[] images) {
            this.type = type;
            this.images = images;
        }

        private Parcelable[] images;
        private String type;

        @Override
        public int getCount() {
            /* Images array size */
            return posters.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            Context context = getApplicationContext();
            final ImageView imageView = new ImageView(context);

            final int pos = position;
            final String file_path;
            if (type.equals("Posters")) {
                file_path = ((Poster) images[pos]).file_path;
            } else {
                file_path = ((Backdrop) images[pos]).file_path;
            }
            new Thread(new Runnable() {
                String url = "http://image.tmdb.org/t/p/w1000/" + file_path + "/?api_key=b3b1492d3e91e9f9403a2989f3031b0c";
                Bitmap bitmap = null;

                @Override
                public void run() {
                    try {
                        bitmap = HttpHandler.fetchImage(url);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                                ((ViewPager) container).addView(imageView, 0);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poster, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.poster_instructions:
                HelpActivity.saveData("first", false);
                HelpActivity.instructions(this);
                return true;
            case R.id.Posters:

                try {
                    ImagePagerAdapter adapter = new ImagePagerAdapter("Posters", posters);
                    viewPager.setAdapter(adapter);
                } catch (Exception e) {
                    Log.d("TMDB", e.getMessage());
                }

                return true;

            case R.id.Backdrops:

                try {
                    ImagePagerAdapter adapter = new ImagePagerAdapter("BackDrops", backdrops);
                    viewPager.setAdapter(adapter);
                } catch (Exception e) {
                    Log.d("TMDB", e.getMessage());
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
