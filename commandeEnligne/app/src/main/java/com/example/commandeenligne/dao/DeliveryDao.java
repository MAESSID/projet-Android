package com.example.commandeenligne.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;
import com.example.commandeenligne.model.Delivery;
import java.util.List;

@Dao
public interface DeliveryDao {
    @Insert
    long insert(Delivery delivery);

    @Query("SELECT * FROM deliveries")
    LiveData<List<Delivery>> getAllDeliveries();

    @Query("SELECT * FROM deliveries WHERE id = :deliveryId")
    LiveData<Delivery> getDeliveryById(int deliveryId);

    @Query("SELECT * FROM deliveries WHERE order_id = :orderId")
    LiveData<List<Delivery>> getDeliveriesByOrderId(int orderId);

    @Update
    void update(Delivery delivery);

    @Delete
    void delete(Delivery delivery);

    @Query("DELETE FROM deliveries WHERE id = :deliveryId")
    void deleteById(int deliveryId);
}
