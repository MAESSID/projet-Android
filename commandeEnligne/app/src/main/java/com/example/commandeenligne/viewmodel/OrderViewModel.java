package com.example.commandeenligne.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.commandeenligne.model.Order;
import com.example.commandeenligne.repository.OrderRepository;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository repository;
    private LiveData<List<Order>> allOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        repository = new OrderRepository(application);
        allOrders = repository.getAllOrders();
    }

    public void insert(Order order) {
        repository.insert(order);
    }

    public void update(Order order) {
        repository.update(order);
    }

    public void delete(Order order) {
        repository.delete(order);
    }

    public void deleteById(int orderId) {
        repository.deleteById(orderId);
    }

    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }

    public LiveData<Order> getOrderById(int orderId) {
        return repository.getOrderById(orderId);
    }
}
