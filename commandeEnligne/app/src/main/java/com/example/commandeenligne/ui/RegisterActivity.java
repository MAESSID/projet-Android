package com.example.commandeenligne.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.commandeenligne.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button registerButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(name)) {
            nameInput.setError("Nom requis");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email requis");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Mot de passe requis");
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Le mot de passe doit contenir au moins 6 caractères");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Les mots de passe ne correspondent pas");
            return;
        }

        // Créer un utilisateur avec Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Inscription réussie
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Enregistrer les informations supplémentaires dans la base de données
                            String userId = firebaseUser.getUid();
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("name", name);
                            userMap.put("email", email);

                            mDatabase.child("users").child(userId).setValue(userMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, 
                                                "Inscription réussie", Toast.LENGTH_SHORT).show();
                                            
                                            // Rediriger vers MainActivity
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Log.w(TAG, "Erreur lors de l'enregistrement des données utilisateur", 
                                                task.getException());
                                        }
                                    }
                                });
                        }
                    } else {
                        // Échec de l'inscription
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, 
                            "Échec de l'inscription : " + task.getException().getMessage(), 
                            Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
