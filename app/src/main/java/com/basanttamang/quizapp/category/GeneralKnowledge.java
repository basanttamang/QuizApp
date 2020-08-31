package com.basanttamang.quizapp.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basanttamang.quizapp.R;
import com.basanttamang.quizapp.SetsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GeneralKnowledge extends AppCompatActivity {

    private GridView setGrid;
    private FirebaseFirestore firestore;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("General Knowledge");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setGrid = findViewById(R.id.setGrid);
        firestore = FirebaseFirestore.getInstance();
        loadSets();

//        SetsAdapter adapter = new SetsAdapter(this,10);
//        setGrid.setAdapter(adapter);

    }

    private void loadSets() {

        firestore.collection("Quiz").document("CAT1")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        long sets = (long)doc.get("SETS");
                        SetsAdapter adapter = new SetsAdapter((int) sets);
                        setGrid.setAdapter(adapter);

                    }else{
                        Toast.makeText(GeneralKnowledge.this, "Not any sets", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(GeneralKnowledge.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
