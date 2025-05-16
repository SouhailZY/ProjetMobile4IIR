package com.example.quiz_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz3 extends AppCompatActivity {
    Button bnext;
    RadioGroup rg;
    RadioButton rb;
    int score = 0;
    String bonneReponse = "3";
    TextView timerTextView;
    CountDownTimer countDownTimer;
    int timeLeft = 15; // 15 secondes chrono

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz3);

        rg = findViewById(R.id.rb1);
        bnext = findViewById(R.id.button);
        timerTextView = findViewById(R.id.timerTextView);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        startTimer();

        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000;
                timerTextView.setText("Temps restant : " + secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(Quiz3.this, "Temps écoulé !", Toast.LENGTH_SHORT).show();
                nextQuestion();
            }
        }.start();
    }

    private void nextQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop chrono si l'utilisateur clique
        }

        int selectedId = rg.getCheckedRadioButtonId();
        if (selectedId != -1) {
            rb = findViewById(selectedId);
            if (rb.getText().toString().equals(bonneReponse)) {
                score++;
            }
        }

        // Aller à Quiz4
        Intent i = new Intent(Quiz3.this, Quiz4.class);
        i.putExtra("score", score);
        startActivity(i);
        finish();
    }
}
