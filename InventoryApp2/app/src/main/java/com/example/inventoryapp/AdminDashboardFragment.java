package com.example.inventoryapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AdminDashboardFragment extends Fragment {
    private EditText etStock, etSales, etOrders;
    private Button btnUpdate, btnReport;
    private TextView tvResults;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        etStock   = v.findViewById(R.id.etStock);
        etSales   = v.findViewById(R.id.etSales);
        etOrders  = v.findViewById(R.id.etOrders);
        btnUpdate = v.findViewById(R.id.btnUpdateStats);
        btnReport = v.findViewById(R.id.btnReport);
        tvResults = v.findViewById(R.id.tvResults);

        // set defaults
        etStock.setText("128");
        etSales.setText("42");
        etOrders.setText("17");

        btnUpdate.setOnClickListener(x -> {
            String sStock = etStock.getText().toString().trim();
            String sSales = etSales.getText().toString().trim();
            String sOrders= etOrders.getText().toString().trim();
            if (TextUtils.isEmpty(sStock) || TextUtils.isEmpty(sSales) || TextUtils.isEmpty(sOrders)) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            String summary = "🔄 Updated Statistics:\n"
                    + "• You have " + sStock + " items in stock.\n"
                    + "• You made "   + sSales + " sales so far.\n"
                    + "• You processed " + sOrders + " orders.";
            tvResults.setText(summary);
        });

        btnReport.setOnClickListener(x ->
                Toast.makeText(getContext(), "Report generated!", Toast.LENGTH_SHORT).show()
        );

        return v;
    }
}
