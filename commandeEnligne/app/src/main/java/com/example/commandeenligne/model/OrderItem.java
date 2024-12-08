package com.example.commandeenligne.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private String productImageUrl;

    public OrderItem() {
        // Required empty constructor for Firebase
    }

    public OrderItem(String productId, String productName, int quantity, double price, String productImageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productImageUrl = productImageUrl;
    }

    // Getters and Setters
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
