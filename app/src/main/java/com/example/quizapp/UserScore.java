package com.example.quizapp;

public class UserScore {
    private String username;
    private int score;
    private String userId;

    public UserScore() {
    }

    public UserScore(String username, int score, String userId) {
        this.username = username;
        this.score = score;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

