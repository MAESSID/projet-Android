package com.example.commandeenligne.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.commandeenligne.database.AppDatabase;
import com.example.commandeenligne.dao.DeliveryDao;
import com.example.commandeenligne.model.Delivery;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeliveryRepository {
    private DeliveryDao deliveryDao;
    private LiveData<List<Delivery>> allDeliveries;
    private ExecutorService executorService;

    public DeliveryRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        deliveryDao = database.deliveryDao();
        allDeliveries = deliveryDao.getAllDeliveries();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Delivery delivery) {
        executorService.execute(() -> deliveryDao.insert(delivery));
    }

    public void update(Delivery delivery) {
        executorService.execute(() -> deliveryDao.update(delivery));
    }

    public void delete(Delivery delivery) {
        executorService.execute(() -> deliveryDao.delete(delivery));
    }

    public void deleteById(int deliveryId) {
        executorService.execute(() -> deliveryDao.deleteById(deliveryId));
    }

    public LiveData<List<Delivery>> getAllDeliveries() {
        return allDeliveries;
    }

    public LiveData<Delivery> getDeliveryById(int deliveryId) {
        return deliveryDao.getDeliveryById(deliveryId);
    }

    public LiveData<List<Delivery>> getDeliveriesByOrderId(int orderId) {
        return deliveryDao.getDeliveriesByOrderId(orderId);
    }
}
