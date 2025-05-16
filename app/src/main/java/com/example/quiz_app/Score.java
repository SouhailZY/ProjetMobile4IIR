package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Score extends AppCompatActivity {

    Button bLogout, bTryAgain;
    TextView tvscore;
    ProgressBar pb;
    int score;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        // Initialisation Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bLogout = findViewById(R.id.btnlogout);
        bTryAgain = findViewById(R.id.btntry);
        tvscore = findViewById(R.id.tvscore);
        pb = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);


        int percentage = score * 100 / 5;
        tvscore.setText(percentage + "%");
        pb.setProgress(percentage);

        // 🔥 Enregistrer le score dans Firestore
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail(); // ou user.getDisplayName() si tu utilises un nom
            Map<String, Object> userScore = new HashMap<>();
            userScore.put("email", email);
            userScore.put("score", percentage);

            db.collection("users_scores").document(user.getUid())
                    .set(userScore)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Score.this, "Score enregistré !", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Score.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
                    });
        }

        bLogout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Merci pour votre participation", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Score.this, MainActivity.class));
            finish();
        });

        bTryAgain.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "essaye une autre fois", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Score.this, Quiz1.class));
            finish();
        });

        TextView tvNextPage = findViewById(R.id.tvNextPage);
        tvNextPage.setOnClickListener(v -> {
            Intent intentNext = new Intent(Score.this, Rank.class);
            startActivity(intentNext);
        });
    }
}