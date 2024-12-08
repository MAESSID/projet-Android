package com.example.commandeenligne.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commandeenligne.R;
import com.example.commandeenligne.adapter.OrderItemAdapter;
import com.example.commandeenligne.model.Order;
import com.example.commandeenligne.model.OrderItem;
import com.example.commandeenligne.repository.FirebaseRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateOrderActivity extends AppCompatActivity {

    private EditText etProductName;
    private EditText etProductPrice;
    private EditText etProductQuantity;
    private Button btnAddProduct;
    private Button btnCreateOrder;
    private RecyclerView rvOrderItems;

    private List<OrderItem> orderItems;
    private OrderItemAdapter orderItemAdapter;
    private FirebaseRepository firebaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        initializeViews();
        initializeListeners();

        orderItems = new ArrayList<>();
        orderItemAdapter = new OrderItemAdapter(orderItems);
        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        rvOrderItems.setAdapter(orderItemAdapter);

        firebaseRepository = new FirebaseRepository(getApplication());
    }

    private void initializeViews() {
        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        rvOrderItems = findViewById(R.id.rvOrderItems);
    }

    private void initializeListeners() {
        btnAddProduct.setOnClickListener(v -> addProductToOrder());
        btnCreateOrder.setOnClickListener(v -> createOrder());
    }

    private void addProductToOrder() {
        String productName = etProductName.getText().toString().trim();
        String priceStr = etProductPrice.getText().toString().trim();
        String quantityStr = etProductQuantity.getText().toString().trim();

        if (productName.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            OrderItem item = new OrderItem(
                null, // productId will be generated or managed elsewhere
                productName,
                quantity,
                price,
                null // productImageUrl can be added later
            );

            orderItems.add(item);
            orderItemAdapter.notifyItemInserted(orderItems.size() - 1);
            Toast.makeText(this, "Produit ajouté", Toast.LENGTH_SHORT).show();

            // Clear input fields
            etProductName.setText("");
            etProductPrice.setText("");
            etProductQuantity.setText("");
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Prix et quantité invalides", Toast.LENGTH_SHORT).show();
        }
    }

    private void createOrder() {
        if (orderItems.isEmpty()) {
            Toast.makeText(this, "Veuillez ajouter des produits", Toast.LENGTH_SHORT).show();
            return;
        }

        Order order = new Order();
        order.setCustomerName("Utilisateur Actuel"); // À remplacer par le nom réel
        order.setCustomerPhone(""); // À remplacer par le téléphone réel
        order.setDeliveryAddress(""); // À remplacer par l'adresse réelle
        order.setOrderDate(new Date());
        order.setTotalAmount(calculateTotalAmount());
        order.setStatus("PENDING");

        firebaseRepository.addOrder(order);
        Toast.makeText(this, "Commande créée avec succès", Toast.LENGTH_SHORT).show();
        finish();
    }

    private double calculateTotalAmount() {
        return orderItems.stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
    }
}
