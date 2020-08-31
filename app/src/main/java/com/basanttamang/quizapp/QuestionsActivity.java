package com.basanttamang.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basanttamang.quizapp.category.GeneralKnowledge;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView questionNum, question, countdown;
    Button option1, option2, option3, option4;
    int questionNo;
    private CountDownTimer countDown;
    private int score;
    private FirebaseFirestore firestore;
    private List<Question> questionList;
    private int setNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        question = findViewById(R.id.question);
        questionNum = findViewById(R.id.questionNum);
        countdown = findViewById(R.id.countdown);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        firestore = FirebaseFirestore.getInstance();
        setNo = getIntent().getIntExtra("SETNO",1);
        getQuestionList();
    }

    private void getQuestionList() {
        questionList = new ArrayList<>();
        firestore.collection("Quiz").document("CAT1").collection("SET"+String.valueOf(setNo))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();
                    Log.d("fasfasfasdasdasd",String.valueOf(questions.size()));
                    for (QueryDocumentSnapshot doc : questions){
                        questionList.add(new Question(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))
                                ));
                        setQuestion();
                    }
                }else {
                    Toast.makeText(QuestionsActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setQuestion() {
        countdown.setText(String.valueOf(10));
        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());

        questionNum.setText(String.valueOf(1) + "/" +String.valueOf(questionList.size()));

        startTimer();
        questionNo = 0;
    }

    private void startTimer() {
         countDown = new CountDownTimer(12000,1000) {
            @Override
            public void onTick(long l) {
                if (l<10000)
                countdown.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        countDown.start();
    }

    private void changeQuestion() {
        if (questionNo < questionList.size()-1){
            questionNo++;
            playAnim(question,0,0);
            playAnim(option1,0,1);
            playAnim(option2,0,2);
            playAnim(option3,0,3);
            playAnim(option4,0,4);

            questionNum.setText(String.valueOf(questionNo+1)+"/"+String.valueOf(questionList.size()));
            countdown.setText(String.valueOf(10));
            startTimer();
        }else{
            Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
            intent.putExtra("SCORE",String.valueOf(score) + "/"+String.valueOf(questionList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //QuestionsActivity.this.finish();
        }
    }

    // animation for changing question
    private void playAnim(final View view, final int value, final int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (value == 0){
                    switch (viewNum){
                        case 0:
                            ((TextView)view).setText(questionList.get(questionNo).getQuestion());
                            break;
                        case 1:
                            ((Button)view).setText(questionList.get(questionNo).getOptionA());
                            break;
                        case 2:
                            ((Button)view).setText(questionList.get(questionNo).getOptionB());
                            break;
                        case 3:
                            ((Button)view).setText(questionList.get(questionNo).getOptionC());
                            break;
                        case 4:
                            ((Button)view).setText(questionList.get(questionNo).getOptionD());
                            break;
                    }
                    if (viewNum != 0){
                        ((Button)view).setBackgroundColor(Color.parseColor("#342DE1"));
                    }
                    playAnim(view,1,viewNum);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        int optionSelected = 0;
        switch (v.getId()){
            case R.id.option1:
                optionSelected =1;
                break;
            case R.id.option2:
                optionSelected=2;
                break;
            case R.id.option3:
                optionSelected=3;
                break;
            case R.id.option4:
                optionSelected=4;
                break;
            default:
        }
        countDown.cancel();
        checkAnswer(optionSelected,v);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(int optionSelected, View v) {
        if (optionSelected == questionList.get(questionNo).getCorrectAns()){
            //right answer
            ((Button)v).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        }else{
            //wrong answer
            ((Button)v).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            switch (questionList.get(questionNo).getCorrectAns()){
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        },2000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDown.cancel();
    }
}