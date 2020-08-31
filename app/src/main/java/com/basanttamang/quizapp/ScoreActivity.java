package com.basanttamang.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    TextView score;
    Button doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score=findViewById(R.id.score);
        doneBtn=findViewById(R.id.doneBtn);

        String score_str = getIntent().getStringExtra("SCORE");
        score.setText(score_str);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this,Home.class);
                ScoreActivity.this.startActivity(intent);
                ScoreActivity.this.finish();
            }
        });
    }
}