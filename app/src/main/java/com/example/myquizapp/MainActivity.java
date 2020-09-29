package com.example.myquizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myquizapp.Bookmark.BookmarksActivity;
import com.example.myquizapp.Categories.CategoriesActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private Button startBtn, bookmarks_btn,retry_btn;

    private Animation uptodown, downtoup;
    private FrameLayout frameLayout;

    //BackpressButton Variable
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected(MainActivity.this)) {
            showCustomDialog();
        }else{
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout2);
        startBtn = findViewById(R.id.start_btn);
        bookmarks_btn = findViewById(R.id.Bookmarks_btn);

        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        frameLayout.setAnimation(uptodown);
        startBtn.setAnimation(downtoup);
        bookmarks_btn.setAnimation(downtoup);

        // For Mobile Ads
        MobileAds.initialize(this);
        loadAds();

        // Start Quiz
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(categoryIntent);
            }
        });

        // Goto Bookmark Activity
        bookmarks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookmarkIntent = new Intent(MainActivity.this, BookmarksActivity.class);
                startActivity(bookmarkIntent);
            }
        });
    }

}

    private void loadAds() {
       AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    //BackPressed Button Code
    public void onBackPressed(){
        if(backPressedTime + 2000>System.currentTimeMillis())
        {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else
        {
            backToast = Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    //Check Internet
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null  &&  wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    //NoInternet Popup view
    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_fragment, viewGroup, false);
        retry_btn = dialogView.findViewById(R.id.retry_btn);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call recreate
                recreate();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
