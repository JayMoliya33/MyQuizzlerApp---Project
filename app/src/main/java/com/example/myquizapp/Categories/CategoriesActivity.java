package com.example.myquizapp.Categories;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myquizapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview = findViewById(R.id.recView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerview.setLayoutManager(layoutManager);

        List<CategoryModel> list = new ArrayList<>();
        list.add(new CategoryModel("", "Category1"));
        list.add(new CategoryModel("", "Category1"));
        list.add(new CategoryModel("", "Category1"));
        list.add(new CategoryModel("", "Category1"));
        list.add(new CategoryModel("", "Category5"));
        list.add(new CategoryModel("", "Category1"));
        list.add(new CategoryModel("", "Category1"));
        list.add(new CategoryModel("", "Category9"));
        list.add(new CategoryModel("", "Category10"));

        CategoryAdapter adapter = new CategoryAdapter(list);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
