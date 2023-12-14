package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
            openAddProviderDialog(Gravity.CENTER);
        });

        //register context menu for list view
        registerForContextMenu(providerListView);
    }

    //set toolbar to action bar
    private void manipulateToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //display data to listview
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

    //create context menu for listview
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    //set onclick event for context menu item
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.context_view)
            Toast.makeText(ProviderActivity.this, "Ban da chon xem o vi tri: "
                    + info.position, Toast.LENGTH_LONG).show();
        else if (item.getItemId() == R.id.context_delete)
            Toast.makeText(ProviderActivity.this, "Ban da chon delete o vi tri: "
                    + info.position, Toast.LENGTH_LONG).show();
        else if (item.getItemId() == R.id.context_update)
            Toast.makeText(ProviderActivity.this, "Ban da chon update o vi tri : "
                    + info.position, Toast.LENGTH_LONG).show();

        return super.onContextItemSelected(item);
    }


    //create topbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    //set onclick event for topbar menu item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        else if (item.getItemId() == R.id.sellStack) {
            Toast.makeText(ProviderActivity.this, "Chuc nang chua hoan thanh", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openAddProviderDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_provider);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        dialog.setCanceledOnTouchOutside(true);

        ImageView imgAddProvider = dialog.findViewById(R.id.imgProviderAddChoose);
        EditText edtProviderName = dialog.findViewById(R.id.edtProviderNameAdd);
        Button btnCloseProviderDialog = dialog.findViewById(R.id.btnCloseProviderAddDialog);
        Button btnSaveToAddNewProvider = dialog.findViewById(R.id.btnAddProviderDialog);

        btnCloseProviderDialog.setOnClickListener(v -> dialog.dismiss());

        btnSaveToAddNewProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProviderActivity.this, "Chuc nang chua hoan thanh", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }
}