package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.dto.Provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderActivity extends AppCompatActivity {

    ListView providerListView;
    List<Provider> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        references();
        displayListView();
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
        providerListView = findViewById(R.id.listViewCate);
    }
}