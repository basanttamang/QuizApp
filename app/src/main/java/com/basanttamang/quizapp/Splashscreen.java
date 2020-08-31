package com.basanttamang.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Splashscreen extends AppCompatActivity {

    ImageView logo;
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);


        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);
        new Thread() {
            public void run() {
                // sleep(3000);
                Intent intent = new Intent(Splashscreen.this, Home.class);
                startActivity(intent);


            }
        }.start();
    }


}