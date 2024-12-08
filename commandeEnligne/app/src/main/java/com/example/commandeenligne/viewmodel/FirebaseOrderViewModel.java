package com.example.commandeenligne.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.commandeenligne.model.FirebaseOrder;
import com.example.commandeenligne.model.Order;
import com.example.commandeenligne.model.OrderItem;
import com.example.commandeenligne.repository.FirebaseRepository;
import com.example.commandeenligne.utils.ConversionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class FirebaseOrderViewModel extends AndroidViewModel {
    private FirebaseRepository repository;
    private LiveData<List<FirebaseOrder>> allFirebaseOrders;
    private LiveData<List<Order>> allOrders;

    public FirebaseOrderViewModel(@NonNull Application application) {
        super(application);
        repository = new FirebaseRepository(application);
        allFirebaseOrders = repository.getAllOrders();
        
        // Conversion des FirebaseOrder en Order
        allOrders = Transformations.map(allFirebaseOrders, firebaseOrders -> 
            firebaseOrders.stream()
                .map(this::convertFirebaseOrderToOrder)
                .collect(Collectors.toList())
        );
    }

    public LiveData<List<FirebaseOrder>> getAllFirebaseOrders() {
        return allFirebaseOrders;
    }

    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }

    public void addOrder(FirebaseOrder order) {
        repository.addOrder(order.toOrder());
    }

    public void updateOrder(String orderId, FirebaseOrder order) {
        repository.updateOrder(orderId, order.toOrder());
    }

    public void deleteOrder(String orderId) {
        repository.deleteOrder(orderId);
    }

    // Méthode de conversion
    private Order convertFirebaseOrderToOrder(FirebaseOrder firebaseOrder) {
        return firebaseOrder.toOrder();
    }

    // Méthode de conversion inverse
    private FirebaseOrder convertOrderToFirebaseOrder(Order order) {
        return new FirebaseOrder(order);
    }

    // Méthode de conversion pour OrderItem
    private OrderItem convertFirebaseOrderItemToOrderItem(FirebaseOrder.OrderItem firebaseOrderItem) {
        OrderItem orderItem = new OrderItem();
        
        orderItem.setProductId(firebaseOrderItem.getProductId());
        orderItem.setProductName(firebaseOrderItem.getProductName());
        orderItem.setQuantity(firebaseOrderItem.getQuantity());
        orderItem.setPrice(firebaseOrderItem.getUnitPrice());
        
        return orderItem;
    }

    // Méthode de conversion inverse pour OrderItem
    private Order.OrderItem convertOrderItemToFirebaseOrderItem(OrderItem orderItem) {
        Order.OrderItem firebaseOrderItem = new Order.OrderItem();
        
        firebaseOrderItem.setProductId(orderItem.getProductId());
        firebaseOrderItem.setProductName(orderItem.getProductName());
        firebaseOrderItem.setQuantity(orderItem.getQuantity());
        firebaseOrderItem.setPrice(orderItem.getPrice());
        
        return firebaseOrderItem;
    }
}
