package com.example.commandeenligne.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.commandeenligne.R;
import com.example.commandeenligne.model.FirebaseDelivery;
import com.example.commandeenligne.viewmodel.FirebaseDeliveryViewModel;
import com.example.commandeenligne.utils.ConversionUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DeliveryDetailActivity extends AppCompatActivity {
    private FirebaseDeliveryViewModel deliveryViewModel;
    private TextInputEditText editTextOrderId, editTextDeliveryPerson, 
                               editTextDeliveryPersonPhone, editTextNotes;
    private AutoCompleteTextView autoCompleteDeliveryStatus;
    private TextView textViewScheduledDeliveryTime, textViewActualDeliveryTime;
    private MaterialButton buttonSaveDelivery;

    private FirebaseDelivery currentDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);

        initializeViews();
        setupViewModel();
        setupDeliveryStatusDropdown();
        handleIncomingIntent();
    }

    private void initializeViews() {
        editTextOrderId = findViewById(R.id.editTextOrderId);
        editTextDeliveryPerson = findViewById(R.id.editTextDeliveryPerson);
        editTextDeliveryPersonPhone = findViewById(R.id.editTextDeliveryPersonPhone);
        editTextNotes = findViewById(R.id.editTextNotes);
        autoCompleteDeliveryStatus = findViewById(R.id.autoCompleteDeliveryStatus);
        textViewScheduledDeliveryTime = findViewById(R.id.textViewScheduledDeliveryTime);
        textViewActualDeliveryTime = findViewById(R.id.textViewActualDeliveryTime);
        buttonSaveDelivery = findViewById(R.id.buttonSaveDelivery);
    }

    private void setupViewModel() {
        deliveryViewModel = new ViewModelProvider(this).get(FirebaseDeliveryViewModel.class);
    }

    private void setupDeliveryStatusDropdown() {
        String[] statuses = {"SCHEDULED", "IN_TRANSIT", "DELIVERED", "FAILED", "CANCELLED"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(
            this, 
            android.R.layout.simple_dropdown_item_1line, 
            statuses
        );
        autoCompleteDeliveryStatus.setAdapter(statusAdapter);
    }

    private void handleIncomingIntent() {
        String deliveryId = getIntent().getStringExtra("DELIVERY_ID");
        
        if (deliveryId != null) {
            // Ici, vous devrez implémenter une méthode pour récupérer un delivery par son ID
            // Cette méthode n'existe pas encore dans le ViewModel actuel
            currentDelivery = new FirebaseDelivery(); // Placeholder
            populateDeliveryDetails(currentDelivery);
        } else {
            currentDelivery = new FirebaseDelivery();
            currentDelivery.setScheduledDeliveryTime(new Date().getTime());
            updateDeliveryTimeDisplay();
        }

        buttonSaveDelivery.setOnClickListener(v -> saveDelivery());
    }

    private void populateDeliveryDetails(FirebaseDelivery delivery) {
        editTextOrderId.setText(delivery.getOrderId());
        editTextDeliveryPerson.setText(delivery.getDeliveryPerson());
        editTextDeliveryPersonPhone.setText(delivery.getDeliveryPersonPhone());
        editTextNotes.setText(delivery.getNotes());
        autoCompleteDeliveryStatus.setText(delivery.getStatus());
        updateDeliveryTimeDisplay();
    }

    private void updateDeliveryTimeDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        
        textViewScheduledDeliveryTime.setText(String.format("Scheduled Delivery Time: %s", 
            currentDelivery.getScheduledDeliveryTime() != null && currentDelivery.getScheduledDeliveryTime() > 0 ? 
            dateFormat.format(new Date(currentDelivery.getScheduledDeliveryTime())) : "Not set"));
        
        textViewActualDeliveryTime.setText(String.format("Actual Delivery Time: %s", 
            currentDelivery.getActualDeliveryTime() != null && currentDelivery.getActualDeliveryTime() > 0 ? 
            dateFormat.format(new Date(currentDelivery.getActualDeliveryTime())) : "Not delivered"));
    }

    private void saveDelivery() {
        try {
            currentDelivery.setOrderId(editTextOrderId.getText().toString());
            currentDelivery.setDeliveryPerson(editTextDeliveryPerson.getText().toString());
            currentDelivery.setDeliveryPersonPhone(editTextDeliveryPersonPhone.getText().toString());
            currentDelivery.setNotes(editTextNotes.getText().toString());
            currentDelivery.setStatus(autoCompleteDeliveryStatus.getText().toString());

            // Vérifier si c'est une nouvelle livraison ou une mise à jour
            if (currentDelivery.getId() == null) {
                deliveryViewModel.addDelivery(currentDelivery);
                Toast.makeText(this, "Delivery Created", Toast.LENGTH_SHORT).show();
            } else {
                deliveryViewModel.updateDelivery(currentDelivery.getId(), currentDelivery);
                Toast.makeText(this, "Delivery Updated", Toast.LENGTH_SHORT).show();
            }
            
            // Fermer l'activité
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error saving delivery: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
