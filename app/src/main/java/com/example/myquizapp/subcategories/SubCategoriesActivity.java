package com.example.myquizapp.subcategories;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myquizapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubCategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    public final String TAG = getClass().getSimpleName();
    private  List<SubCategoryModel> list ;

    private String categorytitle;
    private int categoryposition;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("categorytitle"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // For Mobile Ads
        loadAds();

        // Get Category and setNo
        categorytitle = getIntent().getStringExtra("categorytitle");
        categoryposition = getIntent().getIntExtra("categoryposition",0);

        // Create Loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.setCancelable(false);
        // set dialog Width & Height
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        // set Rounded Loading Dialog
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners));

        // Firebase Database Reference
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        recyclerview = findViewById(R.id.recView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        final SubCategoryAdapter adapter = new SubCategoryAdapter(list);
        recyclerview.setAdapter(adapter);

        loadingDialog.show();

        final String userId = myRef.getKey();
        // Read Data from Firebase
            myRef.child("Category").child(String.valueOf(categoryposition+1)).child("subcategory").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: dataSnapshot "+dataSnapshot.getValue());
                        list.add(dataSnapshot1.getValue(SubCategoryModel.class));
                    }
                    adapter.notifyDataSetChanged();
                    loadingDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                    Toast.makeText(SubCategoriesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                    finish();
                }
            });
        }

    // Finish Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadAds() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
