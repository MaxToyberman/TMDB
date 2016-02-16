package com.toyberman.tmdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import tmdb.entities.YouTube;


public class TrailersActivity extends AppCompatActivity {

    private Parcelable[] youtube;
    private WebView web_view;
    private Spinner spinner;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);
        //finding the spinner
        spinner = (Spinner) findViewById(R.id.spinner_trailers);
        //finding the webview
        web_view = (WebView) findViewById(R.id.web_view);
        progressDialog = ProgressDialog.show(TrailersActivity.this, "", "Loading...", true);

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setAllowFileAccess(true);
        web_view.setWebChromeClient(new WebChromeClient() {
        });

        web_view.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });
        //getting the youtube array
        Intent intent = getIntent();
        youtube = intent.getParcelableArrayExtra("Trailers");
        //getting the trailers list
        fillspinner();
        //init Events
        initEvents();

    }

    @Override
    public void onPause() {
        super.onPause();


        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(web_view, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void initEvents() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String source = ((YouTube) youtube[position]).source;


                String html = "<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                        + source
                        + "?fs=0\" frameborder=\"0\">\n"
                        + "</iframe>\n";
                web_view.loadData(html, "text/html", null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillspinner() {
        //arraylust that will hold the names of the trailers
        ArrayList<String> names = new ArrayList<String>();
        //adding names to array
        for (Parcelable parcel : youtube)
            names.add(((YouTube) parcel).name);
        //creating array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        //creating dropdownview
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //setting the spinner
        spinner.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trailers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.trailers_instructions:
                HelpActivity.saveData("first",false);
                HelpActivity.instructions(this);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
