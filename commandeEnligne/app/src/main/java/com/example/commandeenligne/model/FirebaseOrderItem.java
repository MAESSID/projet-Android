package com.example.commandeenligne.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseOrderItem {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;

    // Constructeur par défaut requis pour Firebase
    public FirebaseOrderItem() {}

    // Constructeur avec paramètres
    public FirebaseOrderItem(String productId, String productName, Integer quantity, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters et Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
