package com.example.commandeenligne.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.commandeenligne.utils.DateConverter;
import com.example.commandeenligne.utils.OrderItemConverter;

import java.util.Date;
import java.util.List;

@Entity(tableName = "orders")
@TypeConverters({DateConverter.class, OrderItemConverter.class})
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "customer_name")
    private String customerName;

    @ColumnInfo(name = "customer_phone")
    private String customerPhone;

    @ColumnInfo(name = "customer_email")
    private String customerEmail;

    @ColumnInfo(name = "delivery_address")
    private String deliveryAddress;

    @ColumnInfo(name = "order_date")
    private Date orderDate;

    @ColumnInfo(name = "total_amount")
    private double totalAmount;

    @ColumnInfo(name = "status")
    private String status; // e.g., "PENDING", "CONFIRMED", "DELIVERED", "CANCELLED"

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "items")
    private List<OrderItem> items;

    // Constructors
    public Order() {}

    @Ignore
    public Order(String customerName, String customerPhone, String customerEmail, String deliveryAddress, 
                 Date orderDate, double totalAmount, String status, String userId, List<OrderItem> items) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.deliveryAddress = deliveryAddress;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.userId = userId;
        this.items = items;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    // Classe interne pour les articles de la commande
    public static class OrderItem {
        private String productId;
        private String productName;
        private int quantity;
        private double price;
        private String productImageUrl;

        public OrderItem() {}

        public OrderItem(String productId, String productName, int quantity, double price, String productImageUrl) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.productImageUrl = productImageUrl;
        }

        // Getters et setters
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public String getProductImageUrl() { return productImageUrl; }
        public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }
    }
}
