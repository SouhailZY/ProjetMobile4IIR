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

public class Quiz2 extends AppCompatActivity {
    Button bnext;
    RadioGroup rg;
    RadioButton rb;
    int score = 0;
    String bonneReponse = "P Bass";
    TextView timerTextView;
    CountDownTimer countDownTimer;
    int timeLeft = 15; // 15 secondes

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

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
                Toast.makeText(Quiz2.this, "Temps écoulé !", Toast.LENGTH_SHORT).show();
                nextQuestion();
            }
        }.start();
    }

    private void nextQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Arrêter le chrono
        }

        int selectedId = rg.getCheckedRadioButtonId();
        if (selectedId != -1) {
            rb = findViewById(selectedId);
            if (rb.getText().toString().equals(bonneReponse)) {
                score++;
            }
        }

        // Passer à l'activité suivante
        Intent i = new Intent(Quiz2.this, Quiz3.class);
        i.putExtra("score", score);
        startActivity(i);
        finish();
    }
}
