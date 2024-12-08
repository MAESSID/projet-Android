package com.example.commandeenligne.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commandeenligne.R;
import com.example.commandeenligne.adapter.OrderItemAdapter;
import com.example.commandeenligne.model.FirebaseOrder;
import com.example.commandeenligne.model.OrderItem;
import com.example.commandeenligne.service.FirebaseService;
import com.example.commandeenligne.viewmodel.FirebaseOrderViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView tvOrderId, tvOrderDate, tvTotalAmount, tvDeliveryAddress;
    private ChipGroup chipGroupStatus;
    private RecyclerView recyclerViewOrderItems;
    private MaterialButton buttonTrackOrder;

    private FirebaseOrderViewModel orderViewModel;
    private FirebaseService firebaseService;
    private OrderItemAdapter orderItemAdapter;
    private FirebaseOrder currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // Configuration de la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.order_details);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setupFirebaseService();
        setupRecyclerView();
        loadOrderDetails();
    }

    private void initializeViews() {
        tvOrderId = findViewById(R.id.tvOrderId);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvDeliveryAddress = findViewById(R.id.tvDeliveryAddress);
        chipGroupStatus = findViewById(R.id.chipGroupStatus);
        recyclerViewOrderItems = findViewById(R.id.recyclerViewOrderItems);
        buttonTrackOrder = findViewById(R.id.buttonTrackOrder);
    }

    private void setupFirebaseService() {
        orderViewModel = new ViewModelProvider(this).get(FirebaseOrderViewModel.class);
        firebaseService = new FirebaseService();
    }

    private void setupRecyclerView() {
        orderItemAdapter = new OrderItemAdapter();
        recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrderItems.setAdapter(orderItemAdapter);
    }

    private void loadOrderDetails() {
        String orderId = getIntent().getStringExtra("ORDER_ID");
        if (orderId == null) {
            Toast.makeText(this, R.string.error_loading_orders, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Utiliser le ViewModel pour charger l'ordre
        orderViewModel.getAllFirebaseOrders().observe(this, firebaseOrders -> {
            for (FirebaseOrder order : firebaseOrders) {
                if (order.getId().equals(orderId)) {
                    currentOrder = order;
                    updateOrderUI();
                    break;
                }
            }
        });
    }

    private void updateOrderUI() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        tvOrderId.setText(getString(R.string.order_id_format, currentOrder.getId()));
        tvOrderDate.setText(dateFormat.format(new Date(currentOrder.getOrderDate())));
        tvTotalAmount.setText(getString(R.string.total_amount_format, currentOrder.getTotalAmount()));
        tvDeliveryAddress.setText(currentOrder.getDeliveryAddress());

        // Convertir FirebaseOrder.OrderItem en OrderItem
        List<OrderItem> convertedOrderItems = currentOrder.getItems().stream()
            .map(this::convertFirebaseOrderItemToOrderItem)
            .collect(Collectors.toList());

        // Configurer les chips de statut
        setupStatusChips();

        // Mettre à jour la liste des articles
        orderItemAdapter.setOrderItems(convertedOrderItems);

        // Configurer le bouton de suivi de commande
        setupTrackOrderButton();
    }

    // Méthode de conversion de FirebaseOrder.OrderItem à OrderItem
    private OrderItem convertFirebaseOrderItemToOrderItem(FirebaseOrder.OrderItem firebaseOrderItem) {
        OrderItem orderItem = new OrderItem();
        
        orderItem.setProductId(firebaseOrderItem.getProductId());
        orderItem.setProductName(firebaseOrderItem.getProductName());
        orderItem.setQuantity(firebaseOrderItem.getQuantity());
        orderItem.setPrice(firebaseOrderItem.getUnitPrice());
        
        return orderItem;
    }

    private void setupStatusChips() {
        chipGroupStatus.removeAllViews();
        
        // Créer une chip pour le statut actuel
        Chip currentStatusChip = new Chip(this);
        currentStatusChip.setText(currentOrder.getStatus());
        currentStatusChip.setChipBackgroundColorResource(getStatusColorResource(currentOrder.getStatus()));
        currentStatusChip.setTextColor(getColor(R.color.white));
        currentStatusChip.setCheckable(false);
        
        chipGroupStatus.addView(currentStatusChip);
    }

    private void setupTrackOrderButton() {
        buttonTrackOrder.setOnClickListener(v -> {
            // TODO: Implémenter la logique de suivi de commande
            Toast.makeText(this, "Fonctionnalité de suivi à venir", Toast.LENGTH_SHORT).show();
        });
    }

    private int getStatusColorResource(String status) {
        switch (status) {
            case "PENDING": return R.color.status_pending;
            case "CONFIRMED": return R.color.status_confirmed;
            case "PROCESSING": return R.color.status_processing;
            case "SHIPPED": return R.color.status_shipped;
            case "DELIVERED": return R.color.status_delivered;
            case "CANCELLED": return R.color.status_cancelled;
            default: return R.color.text_secondary;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
