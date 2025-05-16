package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Rank extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaderboardAdapter adapter;
    private List<UserScore> userList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rank);

        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        adapter = new LeaderboardAdapter(userList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        db.collection("users_scores")
                .orderBy("score", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        UserScore user = doc.toObject(UserScore.class);
                        userList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(Rank.this, "Erreur de chargement", Toast.LENGTH_SHORT).show());

        // Redirection vers MainActivity avec le bouton Home
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Rank.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optionnel : ferme l'activité actuelle
        });
    }
}
