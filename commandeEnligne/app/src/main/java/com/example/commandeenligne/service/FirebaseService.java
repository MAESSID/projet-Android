package com.example.commandeenligne.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.commandeenligne.model.FirebaseDelivery;
import com.example.commandeenligne.model.FirebaseOrder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirebaseService {
    private static final String TAG = "FirebaseService";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    public FirebaseService() {
        mDatabase = FirebaseDatabase.getInstance().getReference("commandeEnligne");
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseService(Context context) {
        // Initialize Firebase
        FirebaseApp.initializeApp(context);
        
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mDatabase = firebaseDatabase.getReference("commandeEnligne");
            mAuth = FirebaseAuth.getInstance();
            Log.d(TAG, "Firebase Database initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase", e);
        }
    }

    public DatabaseReference getDatabaseReference() {
        return mDatabase;
    }

    // Méthodes pour les commandes (Orders)
    public void createOrder(FirebaseOrder order, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        if (mAuth.getCurrentUser() == null) {
            onFailureListener.onFailure(new Exception("Utilisateur non connecté"));
            return;
        }

        order.setUserId(mAuth.getCurrentUser().getUid());
        String orderId = order.getId() != null ? order.getId() : UUID.randomUUID().toString();
        
        mDatabase.child("orders").child(orderId)
            .setValue(order.toMap())
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener);
    }

    public void updateOrder(FirebaseOrder order, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        mDatabase.child("orders").child(order.getId())
            .updateChildren(order.toMap())
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener);
    }

    public void deleteOrder(String orderId, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        mDatabase.child("orders").child(orderId)
            .removeValue()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener);
    }

    public void getOrderById(String orderId, OnSuccessListener<FirebaseOrder> onSuccessListener, OnFailureListener onFailureListener) {
        mDatabase.child("orders").child(orderId)
            .get()
            .addOnSuccessListener(snapshot -> {
                if (snapshot.exists()) {
                    FirebaseOrder order = snapshot.getValue(FirebaseOrder.class);
                    if (order != null) {
                        order.setId(snapshot.getKey());
                        onSuccessListener.onSuccess(order);
                    } else {
                        onFailureListener.onFailure(new Exception("Impossible de convertir la commande"));
                    }
                } else {
                    onFailureListener.onFailure(new Exception("Commande non trouvée"));
                }
            })
            .addOnFailureListener(onFailureListener);
    }

    public void getUserOrders(OnSuccessListener<List<FirebaseOrder>> onSuccessListener, OnFailureListener onFailureListener) {
        if (mAuth.getCurrentUser() == null) {
            onFailureListener.onFailure(new Exception("Utilisateur non connecté"));
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        Query ordersQuery = mDatabase.child("orders").orderByChild("userId").equalTo(userId);

        ordersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FirebaseOrder> orders = new ArrayList<>();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    FirebaseOrder order = orderSnapshot.getValue(FirebaseOrder.class);
                    if (order != null) {
                        order.setId(orderSnapshot.getKey());
                        orders.add(order);
                    }
                }
                onSuccessListener.onSuccess(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onFailureListener.onFailure(error.toException());
            }
        });
    }

    // Méthodes pour les livraisons (Deliveries)
    public void createDelivery(FirebaseDelivery delivery, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        if (mAuth.getCurrentUser() == null) {
            onFailureListener.onFailure(new Exception("Utilisateur non connecté"));
            return;
        }

        delivery.setUserId(mAuth.getCurrentUser().getUid());
        String deliveryId = delivery.getId() != null ? delivery.getId() : UUID.randomUUID().toString();
        
        mDatabase.child("deliveries").child(deliveryId)
            .setValue(delivery.toMap())
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener);
    }

    public void updateDelivery(FirebaseDelivery delivery, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        mDatabase.child("deliveries").child(delivery.getId())
            .updateChildren(delivery.toMap())
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener);
    }

    public void deleteDelivery(String deliveryId, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        mDatabase.child("deliveries").child(deliveryId)
            .removeValue()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener);
    }

    public void getDeliveryById(String deliveryId, OnSuccessListener<FirebaseDelivery> onSuccessListener, OnFailureListener onFailureListener) {
        mDatabase.child("deliveries").child(deliveryId)
            .get()
            .addOnSuccessListener(snapshot -> {
                if (snapshot.exists()) {
                    FirebaseDelivery delivery = snapshot.getValue(FirebaseDelivery.class);
                    if (delivery != null) {
                        delivery.setId(snapshot.getKey());
                        onSuccessListener.onSuccess(delivery);
                    } else {
                        onFailureListener.onFailure(new Exception("Impossible de convertir la livraison"));
                    }
                } else {
                    onFailureListener.onFailure(new Exception("Livraison non trouvée"));
                }
            })
            .addOnFailureListener(onFailureListener);
    }

    public void getUserDeliveries(OnSuccessListener<List<FirebaseDelivery>> onSuccessListener, OnFailureListener onFailureListener) {
        if (mAuth.getCurrentUser() == null) {
            onFailureListener.onFailure(new Exception("Utilisateur non connecté"));
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        Query deliveriesQuery = mDatabase.child("deliveries").orderByChild("userId").equalTo(userId);

        deliveriesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FirebaseDelivery> deliveries = new ArrayList<>();
                for (DataSnapshot deliverySnapshot : snapshot.getChildren()) {
                    FirebaseDelivery delivery = deliverySnapshot.getValue(FirebaseDelivery.class);
                    if (delivery != null) {
                        delivery.setId(deliverySnapshot.getKey());
                        deliveries.add(delivery);
                    }
                }
                onSuccessListener.onSuccess(deliveries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onFailureListener.onFailure(error.toException());
            }
        });
    }

    // Méthode utilitaire pour logger les erreurs
    private void logError(String method, Exception e) {
        Log.e(TAG, method + " failed: " + e.getMessage(), e);
    }
}
