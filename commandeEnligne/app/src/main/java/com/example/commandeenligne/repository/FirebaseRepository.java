package com.example.commandeenligne.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.commandeenligne.model.FirebaseOrder;
import com.example.commandeenligne.model.FirebaseDelivery;
import com.example.commandeenligne.model.Order;
import com.example.commandeenligne.model.Delivery;
import com.example.commandeenligne.service.FirebaseService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.List;

public class FirebaseRepository {
    private FirebaseService firebaseService;

    public FirebaseRepository(Application application) {
        this.firebaseService = new FirebaseService();
    }

    public void addOrder(Order order) {
        FirebaseOrder firebaseOrder = new FirebaseOrder(order);
        firebaseService.createOrder(
            firebaseOrder, 
            aVoid -> {}, 
            e -> {}
        );
    }

    public void updateOrder(String orderId, Order order) {
        FirebaseOrder firebaseOrder = new FirebaseOrder(order);
        firebaseService.updateOrder(
            firebaseOrder, 
            aVoid -> {}, 
            e -> {}
        );
    }

    public void deleteOrder(String orderId) {
        firebaseService.deleteOrder(
            orderId, 
            aVoid -> {}, 
            e -> {}
        );
    }

    public LiveData<List<FirebaseOrder>> getAllOrders() {
        MutableLiveData<List<FirebaseOrder>> ordersLiveData = new MutableLiveData<>();
        firebaseService.getUserOrders(
            ordersLiveData::setValue, 
            e -> {}
        );
        return ordersLiveData;
    }

    public void addDelivery(Delivery delivery) {
        FirebaseDelivery firebaseDelivery = new FirebaseDelivery(delivery);
        firebaseService.createDelivery(
            firebaseDelivery, 
            aVoid -> {}, 
            e -> {}
        );
    }

    public void updateDelivery(String deliveryId, Delivery delivery) {
        FirebaseDelivery firebaseDelivery = new FirebaseDelivery(delivery);
        firebaseService.updateDelivery(
            firebaseDelivery, 
            aVoid -> {}, 
            e -> {}
        );
    }

    public void deleteDelivery(String deliveryId) {
        firebaseService.deleteDelivery(
            deliveryId, 
            aVoid -> {}, 
            e -> {}
        );
    }

    public LiveData<List<FirebaseDelivery>> getAllDeliveries() {
        MutableLiveData<List<FirebaseDelivery>> deliveriesLiveData = new MutableLiveData<>();
        firebaseService.getUserDeliveries(
            deliveriesLiveData::setValue, 
            e -> {}
        );
        return deliveriesLiveData;
    }
}
