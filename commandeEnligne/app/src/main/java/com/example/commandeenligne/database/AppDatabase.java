package com.example.commandeenligne.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.commandeenligne.model.Order;
import com.example.commandeenligne.model.Delivery;
import com.example.commandeenligne.dao.OrderDao;
import com.example.commandeenligne.dao.DeliveryDao;
import com.example.commandeenligne.utils.DateConverter;

@Database(entities = {Order.class, Delivery.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract OrderDao orderDao();
    public abstract DeliveryDao deliveryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "commande_enligne_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
