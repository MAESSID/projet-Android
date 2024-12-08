package com.example.commandeenligne.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.commandeenligne.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private TextView userEmailTextView;
    private Button ordersButton, deliveriesButton, logoutButton;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check if user is logged in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Log.w(TAG, "No user logged in, redirecting to LoginActivity");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        } else {
            Log.d(TAG, "User logged in: " + currentUser.getEmail());
        }

        setContentView(R.layout.activity_main);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        try {
            // Initialize Firebase
            FirebaseApp.initializeApp(this);
            Log.d(TAG, "Firebase App Initialization: Started");
            
            // Obtain the FirebaseAnalytics instance
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            Log.d(TAG, "Firebase Analytics Instance: Obtained");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase", e);
        }

        // Initialize UI components
        initializeViews();

        // Check if user is logged in
        checkCurrentUser();
    }

    private void initializeViews() {
        userEmailTextView = findViewById(R.id.userEmailTextView);
        ordersButton = findViewById(R.id.ordersButton);
        deliveriesButton = findViewById(R.id.deliveriesButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Set click listeners
        ordersButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OrderListActivity.class)));
        deliveriesButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DeliveryListActivity.class)));
        logoutButton.setOnClickListener(v -> logoutUser());
    }

    private void checkCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // If no user is signed in, redirect to login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            // Display user's email
            userEmailTextView.setText("Connecté : " + currentUser.getEmail());
        }
    }

    private void logoutUser() {
        mAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCurrentUser();
    }
}
