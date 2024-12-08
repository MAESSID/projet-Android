package com.example.commandeenligne.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.example.commandeenligne.utils.DateConverter;
import com.example.commandeenligne.utils.ConversionUtils;

import java.util.Date;

@Entity(tableName = "deliveries", 
        foreignKeys = @ForeignKey(
            entity = Order.class, 
            parentColumns = "id", 
            childColumns = "order_id", 
            onDelete = ForeignKey.CASCADE),
        indices = {@Index("order_id")})
@TypeConverters({DateConverter.class})
public class Delivery {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "order_id")
    private Long orderId;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "delivery_person_id")
    private String deliveryPersonId;

    @ColumnInfo(name = "gps_location")
    private String gpsLocation;

    @ColumnInfo(name = "delivery_address")
    private String deliveryAddress;

    @ColumnInfo(name = "delivery_fee")
    private double deliveryFee;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "delivery_person")
    private String deliveryPerson;

    @ColumnInfo(name = "delivery_person_phone")
    private String deliveryPersonPhone;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "scheduled_delivery_time")
    private Long scheduledDeliveryTime;

    @ColumnInfo(name = "actual_delivery_time")
    private Long actualDeliveryTime;

    // Constructors
    public Delivery() {}

    @Ignore
    public Delivery(FirebaseDelivery firebaseDelivery) {
        this.id = firebaseDelivery.getId() != null ? Integer.parseInt(firebaseDelivery.getId()) : null;
        this.userId = firebaseDelivery.getUserId();
        this.orderId = firebaseDelivery.getOrderId() != null ? Long.parseLong(firebaseDelivery.getOrderId()) : null;
        this.status = firebaseDelivery.getStatus();
        this.deliveryPersonId = firebaseDelivery.getDeliveryPersonId();
        this.gpsLocation = firebaseDelivery.getGpsLocation();
        this.deliveryAddress = firebaseDelivery.getDeliveryAddress();
        this.deliveryFee = firebaseDelivery.getDeliveryFee();
        this.timestamp = firebaseDelivery.getTimestamp();
        this.deliveryPerson = firebaseDelivery.getDeliveryPerson();
        this.deliveryPersonPhone = firebaseDelivery.getDeliveryPersonPhone();
        this.notes = firebaseDelivery.getNotes();
        this.scheduledDeliveryTime = firebaseDelivery.getScheduledDeliveryTime();
        this.actualDeliveryTime = firebaseDelivery.getActualDeliveryTime();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

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
}
