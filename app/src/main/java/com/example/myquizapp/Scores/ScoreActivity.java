package com.example.myquizapp.Scores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myquizapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ScoreActivity extends AppCompatActivity {

    private TextView score,total;
    private Button doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // For Mobile Ads
        loadAds();

        score = findViewById(R.id.score);
        total = findViewById(R.id.total);
        doneBtn = findViewById(R.id.done_btn);

        score.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total.setText("Out of "+String.valueOf(getIntent().getIntExtra("total",0)));

        // Finish
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //loadAds
    private void loadAds() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
