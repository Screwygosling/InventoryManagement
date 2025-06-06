package com.example.inventoryapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Item> itemList = new ArrayList<>();
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);

        EditText editName = new EditText(this);
        Button btnAdd = new Button(this);

        adapter = new ItemAdapter(itemList);

        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(it -> {
            Fragment sel = it.getItemId() == R.id.nav_admin
                    ? new AdminDashboardFragment()
                    : new InventoryManagerFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, sel)
                    .commit();
            return true;
        });
        nav.setSelectedItemId(R.id.nav_admin);
    }
}
