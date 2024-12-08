package com.example.commandeenligne.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.commandeenligne.model.Delivery;
import com.example.commandeenligne.repository.DeliveryRepository;
import java.util.List;

public class DeliveryViewModel extends AndroidViewModel {
    private DeliveryRepository repository;
    private LiveData<List<Delivery>> allDeliveries;

    public DeliveryViewModel(@NonNull Application application) {
        super(application);
        repository = new DeliveryRepository(application);
        allDeliveries = repository.getAllDeliveries();
    }

    public void insert(Delivery delivery) {
        repository.insert(delivery);
    }

    public void update(Delivery delivery) {
        repository.update(delivery);
    }

    public void delete(Delivery delivery) {
        repository.delete(delivery);
    }

    public void deleteById(int deliveryId) {
        repository.deleteById(deliveryId);
    }

    public LiveData<List<Delivery>> getAllDeliveries() {
        return allDeliveries;
    }

    public LiveData<Delivery> getDeliveryById(int deliveryId) {
        return repository.getDeliveryById(deliveryId);
    }

    public LiveData<List<Delivery>> getDeliveriesByOrderId(int orderId) {
        return repository.getDeliveriesByOrderId(orderId);
    }
}
