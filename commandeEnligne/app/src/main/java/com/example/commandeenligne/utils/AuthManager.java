package com.example.commandeenligne.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.commandeenligne.models.User;

public class AuthManager {
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

    public static Task<AuthResult> registerUser(String email, String password, String name, boolean isAdmin) {
        return auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        User user = new User(
                            firebaseUser.getUid(),
                            email,
                            isAdmin ? "admin" : "client",
                            name
                        );
                        usersRef.child(firebaseUser.getUid()).setValue(user);
                    }
                });
    }

    public static Task<AuthResult> loginUser(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public static void logoutUser() {
        auth.signOut();
    }

    public static FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public static boolean isUserLoggedIn() {
        return getCurrentUser() != null;
    }

    public static DatabaseReference getUserReference(String userId) {
        return usersRef.child(userId);
    }
}
