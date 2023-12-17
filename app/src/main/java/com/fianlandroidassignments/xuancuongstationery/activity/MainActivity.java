package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.fragment.BillFragment;
import com.fianlandroidassignments.xuancuongstationery.fragment.HomeFragment;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.fragment.RevenueFragment;
import com.fianlandroidassignments.xuancuongstationery.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MaterialToolbar toolbar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.close();

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //assign attribute's values
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.homeTopBar);

        //loading home fragment when application start
        replaceFragment(new HomeFragment());


        //change fragment when click bottom nav item
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

        //get action bar base on toolbar was created in current activity
        manipulateToolbar();
    }

    private void references() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        toolbar = findViewById(R.id.homeTopBar);
    }

    /*
    * get toolbar and set toolbar become action bar
    * set arrow back for action bar
    * */
    private void manipulateToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameHomeLayout, fragment);
        fragmentTransaction.commit();
    }

    //create menu in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    //set onclick to menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        } else if (item.getItemId() == R.id.sellStack) {
            Toast.makeText(MainActivity.this, "Chuc nang chua hoan thanh", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}