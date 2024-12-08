package com.example.commandeenligne.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commandeenligne.R;
import com.example.commandeenligne.model.OrderItem;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderItemAdapter() {
        this.orderItems = new ArrayList<>();
    }

    public OrderItemAdapter(List<OrderItem> orderItems) {
        this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
    }

    public void setOrderItems(List<OrderItem> items) {
        this.orderItems = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem item = orderItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivProductImage;
        private final TextView tvProductName;
        private final TextView tvProductQuantity;
        private final TextView tvProductPrice;
        private final NumberFormat currencyFormat;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        }

        public void bind(OrderItem item) {
            tvProductName.setText(item.getProductName());
            tvProductQuantity.setText(String.format(Locale.getDefault(), "Quantité : %d", item.getQuantity()));
            tvProductPrice.setText(currencyFormat.format(item.getPrice() * item.getQuantity()));

            if (item.getProductImageUrl() != null && !item.getProductImageUrl().isEmpty()) {
                Picasso.get()
                    .load(item.getProductImageUrl())
                    .placeholder(R.drawable.ic_placeholder_product)
                    .into(ivProductImage);
            } else {
                ivProductImage.setImageResource(R.drawable.ic_placeholder_product);
            }
        }
    }
}
