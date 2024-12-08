package com.example.commandeenligne.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.commandeenligne.R;
import com.example.commandeenligne.ui.MainActivity;
import com.example.commandeenligne.ui.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des composants
        emailEditText = findViewById(R.id.emailInput);
        passwordEditText = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        // Initialisation de Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Configuration des listeners
        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        // Ajout du bouton de test uniquement en mode développement
        addTestLoginButton();
    }

    private void addTestLoginButton() {
        // Ajouter un bouton de connexion de test en mode développement
        LinearLayout layout = findViewById(R.id.loginLayout);
        
        // Bouton de test login
        Button testLoginButton = new Button(this);
        testLoginButton.setText("Test Login");
        testLoginButton.setOnClickListener(v -> performTestLogin());
        
        // Bouton de création d'utilisateur de test
        Button createTestUserButton = new Button(this);
        createTestUserButton.setText("Create Test User");
        createTestUserButton.setOnClickListener(v -> createTestUser());
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.topMargin = 16; // 16dp de marge
        testLoginButton.setLayoutParams(params);
        createTestUserButton.setLayoutParams(params);
        
        layout.addView(testLoginButton);
        layout.addView(createTestUserButton);
    }

    private void performTestLogin() {
        String testEmail = "test@commandeenligne.com";
        String testPassword = "password123";
        
        mAuth.signInWithEmailAndPassword(testEmail, testPassword)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        Log.d(TAG, "Test login successful");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    Log.w(TAG, "Test login failed", task.getException());
                    Toast.makeText(LoginActivity.this, "Test Login Failed", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void createTestUser() {
        String testEmail = "test@commandeenligne.com";
        String testPassword = "password123";
        
        mAuth.createUserWithEmailAndPassword(testEmail, testPassword)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        Log.d(TAG, "Test user creation successful");
                        Toast.makeText(LoginActivity.this, "Test User Created", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.w(TAG, "Test user creation failed", task.getException());
                    Toast.makeText(LoginActivity.this, "Test User Creation Failed: " + 
                        task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Veuillez saisir l'email et le mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "User logged in: " + user.getEmail());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Échec de l'authentification: " 
                            + (task.getException() != null ? task.getException().getMessage() : "Erreur inconnue"), 
                            Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Login failed with exception", e);
                    Toast.makeText(LoginActivity.this, "Erreur de connexion: " + e.getMessage(), 
                        Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
