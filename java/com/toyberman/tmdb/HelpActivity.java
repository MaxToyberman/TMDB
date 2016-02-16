package com.toyberman.tmdb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    private String[] text;
    private Integer[] images;
    private int index;
    private ImageView iv_helper;
    private TextView tv_help;
    private Button btn_left;
    private Button btn_right;
    private Button btn_continue;
    private CheckBox cb_show;
    private static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        //shared preferences for TMDB
        pref = getSharedPreferences("TMDB", Context.MODE_PRIVATE);

        if (loadData("first") && loadData("checked")) {

            Intent animation_intent = new Intent(this, AnimationActivity.class);
            startActivity(animation_intent);
        }

        initLayout();
        initEvents();

    }

    public static void saveData(String key, boolean data) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    private Boolean loadData(String key) {
        Boolean data = pref.getBoolean(key, false);

        return data;
    }

    private void initEvents() {

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index > 0) {
                    if (!btn_left.isEnabled())
                        btn_left.setEnabled(true);
                }

                if (index >= 3) {
                    btn_right.setEnabled(false);
                    cb_show.setEnabled(true);
                }

                //setting first image
                iv_helper.setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[index], 500, 500));
                //setting first text
                tv_help.setText(text[index]);
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index--;
                if (index == 0) {
                    btn_left.setEnabled(false);

                }
                if (index <= 3) {
                    btn_right.setEnabled(true);
                }
                //setting first image
                iv_helper.setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[index], 500, 500));
                //setting first text
                tv_help.setText(text[index]);
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saving preferences
                saveData("checked", cb_show.isChecked());
                if (!loadData("first")) saveData("first", true);
                Intent animation_intent = new Intent(getApplicationContext(), AnimationActivity.class);
                startActivity(animation_intent);
            }
        });

    }

    public static void instructions(Context ctx) {

        Intent help_intent = new Intent(ctx, HelpActivity.class);
        ctx.startActivity(help_intent);
    }

    private void initLayout() {
        //get resources
        this.text = getResources().getStringArray(R.array.helper_text);
        images = new Integer[]{R.drawable.first, R.drawable.second, R.drawable.third, R.drawable.fourth};

        //find checkBox
        cb_show = (CheckBox) findViewById(R.id.cb_show);
        cb_show.setEnabled(false);
        //initializing index
        index = 0;
        //finding btn cont
        btn_continue = (Button) findViewById(R.id.btn_continue);
        //finding back button
        btn_left = (Button) findViewById(R.id.btn_left);
        //finding back button
        btn_right = (Button) findViewById(R.id.btn_right);
        //not available at the beginig
        btn_left.setEnabled(false);
        //finding Imageview
        iv_helper = (ImageView) findViewById(R.id.iv_helper);
        //finding text view
        tv_help = (TextView) findViewById(R.id.tv_help);
        //setting first image
        iv_helper.setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[0], 500, 500));
        //setting first text
        tv_help.setText(text[0]);
        //setting scroll method
        tv_help.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


}
