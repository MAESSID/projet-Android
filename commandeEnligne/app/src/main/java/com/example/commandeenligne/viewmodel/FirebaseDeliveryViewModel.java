package com.example.commandeenligne.viewmodel;

import android.app.Application;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.commandeenligne.model.FirebaseDelivery;
import com.example.commandeenligne.model.Delivery;
import com.example.commandeenligne.repository.FirebaseRepository;

public class FirebaseDeliveryViewModel extends AndroidViewModel {
    private FirebaseRepository repository;
    private LiveData<List<FirebaseDelivery>> allFirebaseDeliveries;

    public FirebaseDeliveryViewModel(@NonNull Application application) {
        super(application);
        repository = new FirebaseRepository(application);
        allFirebaseDeliveries = repository.getAllDeliveries();
    }

    public LiveData<List<FirebaseDelivery>> getAllFirebaseDeliveries() {
        return allFirebaseDeliveries;
    }

    public void addDelivery(FirebaseDelivery delivery) {
        repository.addDelivery(convertFirebaseDeliveryToDelivery(delivery));
    }

    public void updateDelivery(String deliveryId, FirebaseDelivery delivery) {
        repository.updateDelivery(deliveryId, convertFirebaseDeliveryToDelivery(delivery));
    }

    public void deleteDelivery(String deliveryId) {
        repository.deleteDelivery(deliveryId);
    }

    // Méthode de conversion pour compatibilité avec le repository
    private Delivery convertFirebaseDeliveryToDelivery(FirebaseDelivery firebaseDelivery) {
        Delivery delivery = new Delivery();
        
        delivery.setId(firebaseDelivery.getId() != null ? 
            Integer.parseInt(firebaseDelivery.getId()) : 
            null);
        
        delivery.setOrderId(firebaseDelivery.getOrderId() != null ? 
            Long.parseLong(firebaseDelivery.getOrderId()) : 
            null);
        
        delivery.setDeliveryPerson(firebaseDelivery.getDeliveryPerson());
        delivery.setDeliveryPersonPhone(firebaseDelivery.getDeliveryPersonPhone());
        delivery.setNotes(firebaseDelivery.getNotes());
        
        delivery.setScheduledDeliveryTime(firebaseDelivery.getScheduledDeliveryTime());
        delivery.setActualDeliveryTime(firebaseDelivery.getActualDeliveryTime());
        
        delivery.setStatus(firebaseDelivery.getStatus());
        
        return delivery;
    }
}
