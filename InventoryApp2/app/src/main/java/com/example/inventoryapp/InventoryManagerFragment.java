package com.example.inventoryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InventoryManagerFragment extends Fragment {
    private EditText editItemName, editOrderId, editQuantity;
    private Button buttonAddItem, buttonAddShipment;
    private RecyclerView rvProducts, rvShipments;
    private Spinner spinnerProduct, spinnerStatus;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inventory_manager, container, false);

        // find views
        editItemName    = v.findViewById(R.id.editItemName);
        buttonAddItem   = v.findViewById(R.id.buttonAddItem);
        rvProducts      = v.findViewById(R.id.rvProducts);

        editOrderId     = v.findViewById(R.id.editOrderId);
        editQuantity    = v.findViewById(R.id.editQuantity);
        spinnerProduct  = v.findViewById(R.id.spinnerProduct);
        spinnerStatus   = v.findViewById(R.id.spinnerStatus);
        buttonAddShipment = v.findViewById(R.id.buttonAddShipment);
        rvShipments     = v.findViewById(R.id.rvShipments);

        MainActivity act = (MainActivity) getActivity();
        if (act == null) return v;

        // --- Products list setup ---
        ItemAdapter itemAdapter = new ItemAdapter(act.itemList);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(itemAdapter);

        buttonAddItem.setOnClickListener(x -> {
            String name = editItemName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Enter product name", Toast.LENGTH_SHORT).show();
            } else {
                act.itemList.add(new Item(name, 0));
                itemAdapter.notifyItemInserted(act.itemList.size() - 1);
                editItemName.setText("");
            }
        });

        // --- Product spinner ---
        List<String> productNames = new ArrayList<>();
        for (Item it : act.itemList) productNames.add(it.getName());
        ArrayAdapter<String> prodAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                productNames
        );
        prodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(prodAdapter);
        itemAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override public void onItemRangeInserted(int pos, int count) {
                productNames.clear();
                for (Item it : act.itemList) productNames.add(it.getName());
                prodAdapter.notifyDataSetChanged();
            }
        });

        // --- Status spinner ---
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.shipment_statuses,
                android.R.layout.simple_spinner_item
        );
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        // --- Shipments list setup ---
        ArrayList<Shipment> shipments = new ArrayList<>();
        ShipmentAdapter shipAdapter = new ShipmentAdapter(shipments);
        rvShipments.setLayoutManager(new LinearLayoutManager(getContext()));
        rvShipments.setAdapter(shipAdapter);

        // --- Add Shipment button ---
        buttonAddShipment.setOnClickListener(x -> {
            String oid  = editOrderId.getText().toString().trim();
            String qtyS = editQuantity.getText().toString().trim();
            String prod = (String) spinnerProduct.getSelectedItem();
            String stat = (String) spinnerStatus.getSelectedItem();

            if (oid.isEmpty()) {
                Toast.makeText(getContext(), "Enter order ID", Toast.LENGTH_SHORT).show();
                return;
            }
            if (qtyS.isEmpty()) {
                Toast.makeText(getContext(), "Enter quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyS);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Quantity must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check and reduce stock
            int index = -1;
            for (int i = 0; i < act.itemList.size(); i++) {
                if (act.itemList.get(i).getName().equals(prod)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                Toast.makeText(getContext(), "Product not found", Toast.LENGTH_SHORT).show();
                return;
            }
            Item item = act.itemList.get(index);
            if (item.getQuantity() < qty) {
                Toast.makeText(getContext(), "Insufficient stock", Toast.LENGTH_SHORT).show();
                return;
            }
            item.setQuantity(item.getQuantity() - qty);
            itemAdapter.notifyItemChanged(index);

            // Add shipment
            shipments.add(new Shipment(oid, prod, stat, qty));
            shipAdapter.notifyItemInserted(shipments.size() - 1);

            // clear inputs
            editOrderId.setText("");
            editQuantity.setText("");
            spinnerProduct.setSelection(0);
            spinnerStatus.setSelection(0);
        });

        return v;
    }
}
