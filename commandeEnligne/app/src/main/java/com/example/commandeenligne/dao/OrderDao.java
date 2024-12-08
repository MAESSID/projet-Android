package com.example.commandeenligne.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;
import com.example.commandeenligne.model.Order;
import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    long insert(Order order);

    @Query("SELECT * FROM orders")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM orders WHERE id = :orderId")
    LiveData<Order> getOrderById(int orderId);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

    @Query("DELETE FROM orders WHERE id = :orderId")
    void deleteById(int orderId);
}
