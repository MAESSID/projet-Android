package com.example.commandeenligne.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.commandeenligne.database.AppDatabase;
import com.example.commandeenligne.dao.OrderDao;
import com.example.commandeenligne.model.Order;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {
    private OrderDao orderDao;
    private LiveData<List<Order>> allOrders;
    private ExecutorService executorService;

    public OrderRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        orderDao = database.orderDao();
        allOrders = orderDao.getAllOrders();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Order order) {
        executorService.execute(() -> orderDao.insert(order));
    }

    public void update(Order order) {
        executorService.execute(() -> orderDao.update(order));
    }

    public void delete(Order order) {
        executorService.execute(() -> orderDao.delete(order));
    }

    public void deleteById(int orderId) {
        executorService.execute(() -> orderDao.deleteById(orderId));
    }

    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }

    public LiveData<Order> getOrderById(int orderId) {
        return orderDao.getOrderById(orderId);
    }
}
