package com.example.commandeenligne.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commandeenligne.R;
import com.example.commandeenligne.model.FirebaseOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    private List<FirebaseOrder> orders;
    private Context context;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(FirebaseOrder order);
    }

    public OrderListAdapter(Context context, OnOrderClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.orders = new ArrayList<>();
    }

    public void setOrders(List<FirebaseOrder> newOrders) {
        this.orders.clear();
        this.orders.addAll(newOrders);
        notifyDataSetChanged();
    }

    public void sortByDate() {
        Collections.sort(orders, (o1, o2) -> 
            Long.compare(o2.getOrderDate(), o1.getOrderDate())
        );
        notifyDataSetChanged();
    }

    public void sortByStatus() {
        Collections.sort(orders, (o1, o2) -> 
            o1.getStatus().compareTo(o2.getStatus())
        );
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        FirebaseOrder order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId, tvOrderDate, tvTotalAmount, tvStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onOrderClick(orders.get(position));
                }
            });
        }

        public void bind(FirebaseOrder order) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            
            tvOrderId.setText(context.getString(R.string.order_id_format, order.getId()));
            tvOrderDate.setText(dateFormat.format(new Date(order.getOrderDate())));
            tvTotalAmount.setText(context.getString(R.string.total_amount_format, order.getTotalAmount()));
            tvStatus.setText(order.getStatus());

            // Coloration du statut
            int statusColor = getStatusColor(order.getStatus());
            tvStatus.setTextColor(statusColor);
        }

        private int getStatusColor(String status) {
            switch (status) {
                case "PENDING":
                    return context.getColor(R.color.status_pending);
                case "CONFIRMED":
                    return context.getColor(R.color.status_confirmed);
                case "PROCESSING":
                    return context.getColor(R.color.status_processing);
                case "SHIPPED":
                    return context.getColor(R.color.status_shipped);
                case "DELIVERED":
                    return context.getColor(R.color.status_delivered);
                case "CANCELLED":
                    return context.getColor(R.color.status_cancelled);
                default:
                    return context.getColor(android.R.color.darker_gray);
            }
        }
    }
}
