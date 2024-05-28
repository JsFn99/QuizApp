package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    Button logout, tryAgain;
    private RecyclerView recyclerView;
    private RankingAdapter adapter;
    private List<UserScore> userScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        tryAgain = findViewById(R.id.tryagain);
        logout = findViewById(R.id.logout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userScores = new ArrayList<>();
        adapter = new RankingAdapter(userScores);
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        fetchUserScores();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, Quiz1.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fetchUserScores() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("userScores")
                .orderBy("score", Query.Direction.DESCENDING)
                //.limit(10)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String username = document.getString("username");
                        int score = document.getLong("score").intValue();
                        String userId = document.getId();
                        UserScore userScore = new UserScore(username, score, userId);
                        userScores.add(userScore);
                    }
                    Log.d("RankingActivity", "userScores: " + userScores);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("RankingActivity", "Error fetching user scores", e);
                });
    }
}
