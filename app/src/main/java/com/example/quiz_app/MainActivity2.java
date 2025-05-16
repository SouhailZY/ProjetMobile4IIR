package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;

public class MainActivity2 extends AppCompatActivity {

    EditText etEmail, etPassword, etConfirmPassword;
    Button bRegister;
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etEmail = findViewById(R.id.Email);
        etPassword = findViewById(R.id.Password);
        etConfirmPassword = findViewById(R.id.ConfirmPassword);
        bRegister = findViewById(R.id.Register);

        myAuth = FirebaseAuth.getInstance();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                signUp(mail, password);
            }
        });
    }

    public void signUp(String mail, String password) {
        myAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(MainActivity2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity2.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
