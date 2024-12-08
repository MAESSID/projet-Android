package com.example.commandeenligne.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commandeenligne.R;
import com.example.commandeenligne.model.FirebaseDelivery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DeliveryListAdapter extends ListAdapter<FirebaseDelivery, DeliveryListAdapter.DeliveryViewHolder> {
    private OnDeliveryClickListener listener;

    public DeliveryListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<FirebaseDelivery> DIFF_CALLBACK = new DiffUtil.ItemCallback<FirebaseDelivery>() {
        @Override
        public boolean areItemsTheSame(@NonNull FirebaseDelivery oldItem, @NonNull FirebaseDelivery newItem) {
            return oldItem.getId() != null && oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FirebaseDelivery oldItem, @NonNull FirebaseDelivery newItem) {
            return oldItem.getOrderId().equals(newItem.getOrderId()) &&
                    oldItem.getDeliveryPersonId().equals(newItem.getDeliveryPersonId()) &&
                    oldItem.getStatus().equals(newItem.getStatus());
        }
    };

    public interface OnDeliveryClickListener {
        void onDeliveryClick(FirebaseDelivery delivery);
    }

    public void setOnDeliveryClickListener(OnDeliveryClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery, parent, false);
        return new DeliveryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        FirebaseDelivery currentDelivery = getItem(position);
        holder.bind(currentDelivery);
    }

    class DeliveryViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDeliveryId, textViewOrderId, textViewDeliveryPerson, 
                         textViewScheduledTime, textViewDeliveryStatus;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDeliveryId = itemView.findViewById(R.id.textViewDeliveryId);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewDeliveryPerson = itemView.findViewById(R.id.textViewDeliveryPerson);
            textViewScheduledTime = itemView.findViewById(R.id.textViewScheduledTime);
            textViewDeliveryStatus = itemView.findViewById(R.id.textViewDeliveryStatus);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onDeliveryClick(getItem(position));
                }
            });
        }

        public void bind(FirebaseDelivery delivery) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

            textViewDeliveryId.setText(String.format("Delivery #%s", delivery.getId()));
            textViewOrderId.setText(String.format("Order #%s", delivery.getOrderId()));
            textViewDeliveryPerson.setText(String.format("Delivery Person ID: %s", delivery.getDeliveryPersonId()));
            textViewScheduledTime.setText(String.format("Scheduled: %s", 
                delivery.getScheduledDeliveryTime() != null && delivery.getScheduledDeliveryTime() > 0 ? 
                dateFormat.format(new Date(delivery.getScheduledDeliveryTime())) : "Not scheduled"));
            textViewDeliveryStatus.setText(String.format("Status: %s", delivery.getStatus()));
        }
    }
}
