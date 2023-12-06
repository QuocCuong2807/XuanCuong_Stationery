package com.fianlandroidassignments.xuancuongstationery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
//    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        replaceFragment(new HomeFragment());

//        toolbar = findViewById(R.id.homeTopBar);
//        toolbar.setOnMenuItemClickListener(item -> {
//            if (item.getItemId() == R.id.sellStack) {
//                return false;
//            }
//            return true;
//        });

        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.itemHome) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.itemRevenue) {
                replaceFragment(new RevenueFragment());
            } else if (item.getItemId() == R.id.itemBill) {
                replaceFragment(new BillFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameHomeLayout, fragment);
        fragmentTransaction.commit();
    }
}