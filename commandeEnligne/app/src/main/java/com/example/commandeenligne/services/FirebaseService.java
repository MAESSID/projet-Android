package com.example.commandeenligne.services;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseService {
    private static final String TAG = "FirebaseService";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public FirebaseService(Context context) {
        // Initialize Firebase
        FirebaseApp.initializeApp(context);
        
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("commandeEnligne");
            Log.d(TAG, "Firebase Database initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase", e);
        }
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
