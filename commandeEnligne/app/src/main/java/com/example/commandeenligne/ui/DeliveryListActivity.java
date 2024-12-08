package com.example.commandeenligne.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commandeenligne.R;
import com.example.commandeenligne.adapter.DeliveryListAdapter;
import com.example.commandeenligne.model.FirebaseDelivery;
import com.example.commandeenligne.viewmodel.FirebaseDeliveryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DeliveryListActivity extends AppCompatActivity {
    private FirebaseDeliveryViewModel deliveryViewModel;
    private DeliveryListAdapter adapter;
    private RecyclerView recyclerViewDeliveries;
    private TextView textViewEmptyDeliveryList;
    private FloatingActionButton fabAddDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);

        initializeViews();
        setupRecyclerView();
        setupViewModel();
        setupListeners();
    }

    private void initializeViews() {
        recyclerViewDeliveries = findViewById(R.id.recyclerViewDeliveries);
        textViewEmptyDeliveryList = findViewById(R.id.textViewEmptyDeliveryList);
        fabAddDelivery = findViewById(R.id.fabAddDelivery);
    }

    private void setupRecyclerView() {
        adapter = new DeliveryListAdapter();
        recyclerViewDeliveries.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDeliveries.setAdapter(adapter);

        adapter.setOnDeliveryClickListener(this::openDeliveryDetail);
    }

    private void setupViewModel() {
        deliveryViewModel = new ViewModelProvider(this).get(FirebaseDeliveryViewModel.class);
        deliveryViewModel.getAllFirebaseDeliveries().observe(this, firebaseDeliveries -> {
            // Utiliser directement FirebaseDelivery
            adapter.submitList(firebaseDeliveries);
            textViewEmptyDeliveryList.setVisibility(firebaseDeliveries.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void setupListeners() {
        fabAddDelivery.setOnClickListener(v -> openDeliveryDetail(null));
    }

    private void openDeliveryDetail(FirebaseDelivery delivery) {
        Intent intent = new Intent(this, DeliveryDetailActivity.class);
        if (delivery != null) {
            intent.putExtra("DELIVERY_ID", delivery.getId());
        }
        startActivity(intent);
    }
}
