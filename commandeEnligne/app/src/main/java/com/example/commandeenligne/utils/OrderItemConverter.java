package com.example.commandeenligne.utils;

import androidx.room.TypeConverter;

import com.example.commandeenligne.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class OrderItemConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Order.OrderItem> stringToOrderItemList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Order.OrderItem>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String orderItemListToString(List<Order.OrderItem> orderItems) {
        return orderItems == null ? null : gson.toJson(orderItems);
    }
}
