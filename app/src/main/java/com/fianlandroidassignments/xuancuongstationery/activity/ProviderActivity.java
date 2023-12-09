package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.dto.Provider;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProviderActivity extends AppCompatActivity {

    ListView providerListView;
    List<Provider> providers;
    MaterialToolbar toolbar;
    FloatingActionButton addNewProviderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        references();
        displayListView();
        manipulateToolbar();

        //set onclick event to add new provider button
        addNewProviderBtn.setOnClickListener(view -> {
            Toast.makeText(ProviderActivity.this, "Chức năng thêm mới chưa được phát triển", Toast.LENGTH_LONG).show();
        });
    }

    private void manipulateToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void displayListView() {
        providers = new ArrayList<>();
        ProviderAdapter providerAdapter = new ProviderAdapter(this, providers);

        providers.add(new Provider(1, "Thiên Long Hoàn Cầu", R.drawable.tlhc));
        providers.add(new Provider(2, "Công ty Mai Son", R.drawable.maison));
        providers.add(new Provider(3, "Công ty VPP Hồng Hà", R.drawable.hongha));
        providers.add(new Provider(4, "Văn phòng phẩm Artline", R.drawable.artline));

        providerListView.setAdapter(providerAdapter);
    }

    private void references() {
        toolbar = findViewById(R.id.customTopProviderBar);
        providerListView = findViewById(R.id.listViewProvider);
        addNewProviderBtn = findViewById(R.id.addNewProviderButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        else if (item.getItemId() == R.id.sellStack) {
            Toast.makeText(ProviderActivity.this, "Chuc nang chua hoan thanh", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}