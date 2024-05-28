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

public class Quiz1 extends AppCompatActivity {

    Button next;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String correctAnswer = "Harper Lee";
    CountDownTimer countDownTimer;
    TextView Timer;
    long timeLeftInMillis = 60000;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);
        next= findViewById(R.id.next1);
        radioGroup = findViewById(R.id.optionGroup1);
        Timer = findViewById(R.id.Timer);
        startTimer();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Quiz1.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
                else {
                    radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(correctAnswer)) {
                        score++;
                    }
                    Intent intent = new Intent(Quiz1.this, Quiz2.class);
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
                Intent intent = new Intent(Quiz1.this, Score.class);
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