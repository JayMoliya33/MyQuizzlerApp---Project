package com.example.myquizapp.Bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myquizapp.Questions.QuestionModel;
import com.example.myquizapp.R;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bookmark");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview = findViewById(R.id.rec_bookmark);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        // Dummy Data
        List<QuestionModel> list = new ArrayList<>();
        list.add(new QuestionModel("what is your name?","","","","","Moliya Jay",0));
        list.add(new QuestionModel("what is your name?","","","","","Moliya Jay",0));
        list.add(new QuestionModel("what is your name?","","","","","Moliya Jay",0));
        list.add(new QuestionModel("what is your name?","","","","","Moliya Jay",0));
        list.add(new QuestionModel("what is your name?","","","","","Moliya Jay",0));
        list.add(new QuestionModel("what is your name?","","","","","Moliya Jay",0));
        list.add(new QuestionModel("what is your name?","","","","","Moliya Jay",0));

        BookmarkAdapter adapter = new BookmarkAdapter(list);
        recyclerview.setAdapter(adapter);

    }

    //  goto Home Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
