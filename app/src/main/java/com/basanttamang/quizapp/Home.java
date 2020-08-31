package com.basanttamang.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private RecyclerView categoryRecycle;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        categoryRecycle = findViewById(R.id.categoryRecycle);
        adapter = new CategoryAdapter(this,getData());
        categoryRecycle.setAdapter(adapter);
        categoryRecycle.setLayoutManager(new GridLayoutManager(this,2));
    }

    private ArrayList<Category> getData(){
        ArrayList<Category> categories = new ArrayList<>();
        Category c;

        c = new Category();
        c.setCategoryName("General Knowledge");
        categories.add(c);


        return categories;
    }
}