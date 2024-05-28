package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.color.utilities.Score;

public class Quiz4 extends AppCompatActivity {

    Button next;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String correctAnswer = "Michael Jackson";
    int score;
    TextView Timer;
    CountDownTimer countDownTimer;
    long timeLeftInMillis = 60000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz4);
        next= findViewById(R.id.next4);
        radioGroup = findViewById(R.id.optionGroup1);
        Intent intent2 = getIntent();
        score = intent2.getIntExtra("score", 0);
        Timer = findViewById(R.id.Timer);
        timeLeftInMillis = intent2.getLongExtra("timeLeftInMillis", 60000);
        startTimer();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Quiz4.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
                else {
                    radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(correctAnswer)) {
                        score++;
                    }
                    Intent intent = new Intent(Quiz4.this, Quiz5.class);
                    intent.putExtra("score", score);
                    intent.putExtra("timeLeftInMillis", timeLeftInMillis);
                    startActivity(intent);
                    // overridePendingTransition(R.anim.exit, R.anim.entery);
                    finish();
                }
            }
        });
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                Timer.setText("00:00");
                Intent intent = new Intent(Quiz4.this, Score.class);
                intent.putExtra("finalScore", score);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        Timer.setText(timeLeftFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}