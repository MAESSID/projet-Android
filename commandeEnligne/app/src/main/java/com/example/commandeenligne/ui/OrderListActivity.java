package com.example.commandeenligne.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.commandeenligne.R;
import com.example.commandeenligne.adapter.OrderListAdapter;
import com.example.commandeenligne.model.FirebaseOrder;
import com.example.commandeenligne.service.FirebaseService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrders;
    private TextView textViewEmptyOrderList;
    private FloatingActionButton fabAddOrder;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OrderListAdapter adapter;
    private FirebaseService firebaseService;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        // Configuration de la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.my_orders);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setupFirebaseComponents();
        setupRecyclerView();
        setupListeners();
        loadOrders();
    }

    private void initializeViews() {
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        textViewEmptyOrderList = findViewById(R.id.textViewEmptyOrderList);
        fabAddOrder = findViewById(R.id.fabAddOrder);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void setupFirebaseComponents() {
        firebaseService = new FirebaseService();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setupRecyclerView() {
        adapter = new OrderListAdapter(this, order -> {
            // Ouvrir les détails de la commande
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("ORDER_ID", order.getId());
            startActivity(intent);
        });
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrders.setAdapter(adapter);
    }

    private void setupListeners() {
        fabAddOrder.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateOrderActivity.class);
            startActivity(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(this::loadOrders);
    }

    private void loadOrders() {
        swipeRefreshLayout.setRefreshing(true);
        firebaseService.getUserOrders(
            orders -> {
                swipeRefreshLayout.setRefreshing(false);
                if (orders.isEmpty()) {
                    textViewEmptyOrderList.setVisibility(View.VISIBLE);
                    recyclerViewOrders.setVisibility(View.GONE);
                } else {
                    textViewEmptyOrderList.setVisibility(View.GONE);
                    recyclerViewOrders.setVisibility(View.VISIBLE);
                    adapter.setOrders(orders);
                }
            },
            exception -> {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(findViewById(android.R.id.content), 
                    getString(R.string.error_loading_orders), 
                    Snackbar.LENGTH_LONG).show();
            }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_sort_date) {
            // Trier par date
            adapter.sortByDate();
            return true;
        } else if (itemId == R.id.action_sort_status) {
            // Trier par statut
            adapter.sortByStatus();
            return true;
        } else if (itemId == R.id.action_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
