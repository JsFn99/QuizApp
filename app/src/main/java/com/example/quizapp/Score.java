package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizapp.MainActivity;
import com.example.quizapp.Quiz1;
import com.example.quizapp.R;
import com.example.quizapp.RankingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Score extends AppCompatActivity {

    TextView tvscore;
    ProgressBar progressBar;
    int score;
    Button logout, tryAgain, viewRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tvscore = findViewById(R.id.score);
        progressBar = findViewById(R.id.progressBar);
        tryAgain = findViewById(R.id.tryagain);
        logout = findViewById(R.id.logout);
        viewRanking = findViewById(R.id.viewRanking);

        Intent intent = getIntent();
        score = intent.getIntExtra("finalScore", 0);

        tvscore.setText("Your Score is: " + score*100/5 + "%");
        progressBar.setProgress(score*100/5);

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, Quiz1.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeUserScoreInFirestore();
                Intent intent = new Intent(Score.this, RankingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void storeUserScoreInFirestore() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            String userId = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("username", user.getDisplayName());
        data.put("score", score*100/5);
        db.collection("userScores").document(userId)
                .set(data)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                });
    }
    }
}
