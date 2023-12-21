package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.CursorWindow;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.CategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.RecyclerViewProductAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;

import java.lang.reflect.Field;
import java.util.List;

public class ListProductActivity extends AppCompatActivity {
    List<ProductDTO> productDTOS;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    RecyclerViewProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        databaseHelper = new DatabaseHelper(ListProductActivity.this);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 1000 * 1024 * 1024);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        productDTOS = databaseHelper.selectAllProduct();

        reference();
        setAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registerForContextMenu(recyclerView);
    }

    private void setAdapter() {
        recyclerView.setAdapter(productAdapter);
    }

    private void reference() {
        recyclerView = findViewById(R.id.recyclerViewListProduct);
        productAdapter = new RecyclerViewProductAdapter(ListProductActivity.this, productDTOS);
    }


    //set onclick event to context menu item
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int position = item.getGroupId();
        if (item.getItemId() == R.id.context_product_Update) {
            Toast.makeText(ListProductActivity.this, "Chuc nang chua hoan thanh: " + position, Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.context_product_Sell) {
            Toast.makeText(ListProductActivity.this, "Chuc nang chua hoan thanh: " + position, Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.context_product_delete) {
            Toast.makeText(ListProductActivity.this, "Chuc nang chua hoan thanh: " + position, Toast.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);
    }

    private void displayListView() {
        productDTOS = databaseHelper.selectAllProduct();
        RecyclerViewProductAdapter productAdapter1 = new RecyclerViewProductAdapter(ListProductActivity.this, productDTOS);
        recyclerView.setAdapter(productAdapter1);
    }
}