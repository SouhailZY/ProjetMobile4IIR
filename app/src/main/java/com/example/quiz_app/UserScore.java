package com.example.quiz_app;

public class UserScore {
    private String email;
    private int score;

    public UserScore() {
        // Nécessaire pour Firestore
    }

    public UserScore(String email, int score) {
        this.email = email;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }
}