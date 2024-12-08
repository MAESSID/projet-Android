package com.example.commandeenligne.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebaseDelivery implements Serializable {
    private String id;
    private String userId;
    private String orderId;
    private String status;
    private String deliveryPersonId;
    private String gpsLocation;
    private String deliveryAddress;
    private double deliveryFee;
    private long timestamp;
    private String deliveryPerson;
    private String deliveryPersonPhone;
    private String notes;
    private Long scheduledDeliveryTime;
    private Long actualDeliveryTime;

    public FirebaseDelivery() {
        // Required empty constructor for Firebase
    }

    // Constructeur mis à jour pour gérer les conversions
    public FirebaseDelivery(Delivery delivery) {
        super();
        this.id = delivery.getId() != null ? String.valueOf(delivery.getId()) : null;
        this.userId = delivery.getUserId();
        this.orderId = delivery.getOrderId() != null ? String.valueOf(delivery.getOrderId()) : null;
        this.status = delivery.getStatus();
        this.deliveryPersonId = delivery.getDeliveryPersonId();
        this.gpsLocation = delivery.getGpsLocation();
        this.deliveryAddress = delivery.getDeliveryAddress();
        this.deliveryFee = delivery.getDeliveryFee();
        this.timestamp = delivery.getTimestamp();
        this.deliveryPerson = delivery.getDeliveryPerson();
        this.deliveryPersonPhone = delivery.getDeliveryPersonPhone();
        this.notes = delivery.getNotes();
        this.scheduledDeliveryTime = delivery.getScheduledDeliveryTime();
        this.actualDeliveryTime = delivery.getActualDeliveryTime();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("userId", userId);
        result.put("orderId", orderId);
        result.put("status", status);
        result.put("deliveryPersonId", deliveryPersonId);
        result.put("gpsLocation", gpsLocation);
        result.put("deliveryAddress", deliveryAddress);
        result.put("deliveryFee", deliveryFee);
        result.put("timestamp", timestamp);
        result.put("deliveryPerson", deliveryPerson);
        result.put("deliveryPersonPhone", deliveryPersonPhone);
        result.put("notes", notes);
        result.put("scheduledDeliveryTime", scheduledDeliveryTime);
        result.put("actualDeliveryTime", actualDeliveryTime);
        return result;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDeliveryPersonId() { return deliveryPersonId; }
    public void setDeliveryPersonId(String deliveryPersonId) { this.deliveryPersonId = deliveryPersonId; }

    public String getGpsLocation() { return gpsLocation; }
    public void setGpsLocation(String gpsLocation) { this.gpsLocation = gpsLocation; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getDeliveryPerson() { return deliveryPerson; }
    public void setDeliveryPerson(String deliveryPerson) { this.deliveryPerson = deliveryPerson; }

    public String getDeliveryPersonPhone() { return deliveryPersonPhone; }
    public void setDeliveryPersonPhone(String deliveryPersonPhone) { this.deliveryPersonPhone = deliveryPersonPhone; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getScheduledDeliveryTime() { return scheduledDeliveryTime; }
    public void setScheduledDeliveryTime(Long scheduledDeliveryTime) { this.scheduledDeliveryTime = scheduledDeliveryTime; }

    public Long getActualDeliveryTime() { return actualDeliveryTime; }
    public void setActualDeliveryTime(Long actualDeliveryTime) { this.actualDeliveryTime = actualDeliveryTime; }

    public Delivery toDelivery() {
        Delivery delivery = new Delivery();
        // Utiliser des méthodes de conversion sûres
        if (this.id != null) {
            try {
                delivery.setId(Integer.parseInt(this.id));
            } catch (NumberFormatException e) {
                // Gérer le cas où l'ID ne peut pas être converti
                delivery.setId(null);
            }
        }
        
        try {
            delivery.setOrderId(this.orderId != null ? Long.parseLong(this.orderId) : null);
        } catch (NumberFormatException e) {
            // Gérer le cas où orderId ne peut pas être converti
            delivery.setOrderId(null);
        }
        
        delivery.setUserId(this.userId);
        delivery.setStatus(this.status);
        delivery.setDeliveryPersonId(this.deliveryPersonId);
        delivery.setGpsLocation(this.gpsLocation);
        delivery.setDeliveryAddress(this.deliveryAddress);
        delivery.setDeliveryFee(this.deliveryFee);
        delivery.setTimestamp(this.timestamp);
        delivery.setDeliveryPerson(this.deliveryPerson);
        delivery.setDeliveryPersonPhone(this.deliveryPersonPhone);
        delivery.setNotes(this.notes);
        delivery.setScheduledDeliveryTime(this.scheduledDeliveryTime);
        delivery.setActualDeliveryTime(this.actualDeliveryTime);
        
        return delivery;
    }
}
