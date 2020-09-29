package com.example.myquizapp.Bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myquizapp.Questions.QuestionModel;
import com.example.myquizapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.myquizapp.Questions.QuestionActivity.FIILE_NAME;
import static com.example.myquizapp.Questions.QuestionActivity.KEY_NAME;

public class BookmarksActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    private List<QuestionModel> bookmarksList;
    private SharedPreferences prefrences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        // For Mobile Ads
        loadAds();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Bookmark");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview = findViewById(R.id.rec_bookmark);

        // Shared Preferences
        prefrences = getSharedPreferences(FIILE_NAME, Context.MODE_PRIVATE);
        editor = prefrences.edit();
        gson = new Gson();

        getBookmarks();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        // Dummy Data
        List<QuestionModel> list = new ArrayList<>();

        BookmarkAdapter adapter = new BookmarkAdapter(bookmarksList);
        recyclerview.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
    }


    //  goto Home Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // called in onCreate()
    private void getBookmarks() {
        String json = prefrences.getString(KEY_NAME, "");

        Type type = new TypeToken<List<QuestionModel>>() {
        }.getType();

        bookmarksList = gson.fromJson(json, type);

        if (bookmarksList == null) {
            bookmarksList = new ArrayList<>();
        }
    }

    // called in onPause()
    private void storeBookmarks() {
        String json = gson.toJson(bookmarksList);
        editor.putString(KEY_NAME, json);
        editor.commit();
    }

    private void loadAds() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
