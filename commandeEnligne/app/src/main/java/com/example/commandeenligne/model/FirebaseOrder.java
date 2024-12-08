package com.example.commandeenligne.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FirebaseOrder {
    // Statuts de commande
    public enum OrderStatus {
        PENDING("En attente"),
        CONFIRMED("Confirmée"),
        PROCESSING("En préparation"),
        SHIPPED("Expédiée"),
        DELIVERED("Livrée"),
        CANCELLED("Annulée");

        private final String label;

        OrderStatus(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private String id;
    private String userId; // ID du client qui a passé la commande
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String deliveryAddress;
    private long orderDate;
    private double totalAmount;
    private String status;
    private String trackingCode;
    private List<OrderItem> items;

    // Classe interne pour les articles de la commande
    public static class OrderItem {
        private String productId;
        private String productName;
        private int quantity;
        private double unitPrice;
        private String productImageUrl;

        public OrderItem() {}

        public OrderItem(String productId, String productName, int quantity, double unitPrice, String productImageUrl) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.productImageUrl = productImageUrl;
        }

        // Getters et setters
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getUnitPrice() { return unitPrice; }
        public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
        public String getProductImageUrl() { return productImageUrl; }
        public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }
    }

    public FirebaseOrder() {
        this.id = UUID.randomUUID().toString();
        this.items = new ArrayList<>();
    }

    // Constructeur de copie
    public FirebaseOrder(Order order) {
        this.id = order.getId() != 0 ? String.valueOf(order.getId()) : UUID.randomUUID().toString();
        this.customerName = order.getCustomerName();
        this.customerPhone = order.getCustomerPhone();
        this.deliveryAddress = order.getDeliveryAddress();
        this.orderDate = order.getOrderDate() != null ? order.getOrderDate().getTime() : System.currentTimeMillis();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
        this.trackingCode = generateTrackingCode();
        this.items = new ArrayList<>();
    }

    private String generateTrackingCode() {
        return "CMD-" + System.currentTimeMillis();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("userId", userId);
        result.put("customerName", customerName);
        result.put("customerPhone", customerPhone);
        result.put("customerEmail", customerEmail);
        result.put("deliveryAddress", deliveryAddress);
        result.put("orderDate", orderDate);
        result.put("totalAmount", totalAmount);
        result.put("status", status);
        result.put("trackingCode", trackingCode);
        
        // Convertir les articles de la commande
        List<Map<String, Object>> itemMaps = new ArrayList<>();
        for (OrderItem item : items) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("productId", item.getProductId());
            itemMap.put("productName", item.getProductName());
            itemMap.put("quantity", item.getQuantity());
            itemMap.put("unitPrice", item.getUnitPrice());
            itemMap.put("productImageUrl", item.getProductImageUrl());
            itemMaps.add(itemMap);
        }
        result.put("items", itemMaps);
        
        return result;
    }

    // Méthodes pour gérer les articles de la commande
    public void addItem(OrderItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        recalculateTotalAmount();
    }

    public void removeItem(String productId) {
        if (items != null) {
            items.removeIf(item -> item.getProductId().equals(productId));
            recalculateTotalAmount();
        }
    }

    private void recalculateTotalAmount() {
        totalAmount = 0;
        if (items != null) {
            for (OrderItem item : items) {
                totalAmount += item.getQuantity() * item.getUnitPrice();
            }
        }
    }

    // Getters et setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public long getOrderDate() { return orderDate; }
    public void setOrderDate(long orderDate) { this.orderDate = orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTrackingCode() { return trackingCode; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { 
        this.items = items; 
        recalculateTotalAmount();
    }

    @Exclude
    public Order toOrder() {
        Order order = new Order();
        order.setCustomerName(this.customerName);
        order.setCustomerPhone(this.customerPhone);
        order.setDeliveryAddress(this.deliveryAddress);
        order.setOrderDate(new Date(this.orderDate));
        order.setTotalAmount(this.totalAmount);
        order.setStatus(this.status);
        return order;
    }
}
