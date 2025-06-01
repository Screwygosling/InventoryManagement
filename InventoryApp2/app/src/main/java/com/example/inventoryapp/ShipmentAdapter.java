package com.example.inventoryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.VH> {
    private final List<Shipment> data;

    public ShipmentAdapter(List<Shipment> data) {
        this.data = data;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int pos) {
        Shipment s = data.get(pos);
        // Line 1: OrderID – Product × Qty
        String line1 = "Order " + s.getOrderId()
                + " – " + s.getProductName()
                + " ×" + s.getQuantity();
        // Line 2: Status
        String line2 = s.getStatus();
        ((TextView)holder.itemView.findViewById(android.R.id.text1))
                .setText(line1);
        ((TextView)holder.itemView.findViewById(android.R.id.text2))
                .setText(line2);
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        VH(@NonNull View v) { super(v); }
    }
}
