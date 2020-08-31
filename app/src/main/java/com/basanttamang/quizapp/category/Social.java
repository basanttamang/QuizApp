package com.basanttamang.quizapp.category;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basanttamang.quizapp.R;
import com.basanttamang.quizapp.SetsAdapter;

public class Social extends AppCompatActivity {
    private GridView setGrid;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Social");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setGrid = findViewById(R.id.setGrid);

        SetsAdapter adapter = new SetsAdapter(10);
        setGrid.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
