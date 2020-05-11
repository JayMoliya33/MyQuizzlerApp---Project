package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myquizapp.Bookmark.BookmarksActivity;
import com.example.myquizapp.Categories.CategoriesActivity;

public class MainActivity extends AppCompatActivity {

    private Button startBtn,bookmarks_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Quiz
        startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(categoryIntent);
            }
        });

        // Goto Bookmark Activity
        bookmarks_btn = findViewById(R.id.Bookmarks_btn);
        bookmarks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookmarkIntent = new Intent(MainActivity.this,BookmarksActivity.class);
                startActivity(bookmarkIntent);
            }
        });
    }
}
