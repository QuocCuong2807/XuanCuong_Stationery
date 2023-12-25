package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
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
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.Common.InputFilterMinMax;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.CategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.RecyclerViewProductAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.SellWaitingList;
import com.google.android.material.appbar.MaterialToolbar;

import java.lang.reflect.Field;
import java.util.List;

public class ListProductActivity extends AppCompatActivity {
    List<ProductDTO> productDTOS;
    ProductDTO product;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    RecyclerViewProductAdapter productAdapter;
    MaterialToolbar toolbar;
    EditText edtInputQuantity, edtEditProductName, edtEditProductImportPrice, edtEditProductSellPrice;
    ImageView editProductImage;
    Button  btnCloseAddQuantityDialog, btnSaveAddQuantityDialog, btnCloseEditProductDialog, btnEditProductDialog;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        databaseHelper = new DatabaseHelper(ListProductActivity.this);

        //increase cursor window size
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
        manipulateToolbar();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registerForContextMenu(recyclerView);
        displayImageForImageView();
    }

    private void setAdapter() {
        recyclerView.setAdapter(productAdapter);
    }

    private void reference() {
        recyclerView = findViewById(R.id.recyclerViewListProduct);
        productAdapter = new RecyclerViewProductAdapter(ListProductActivity.this, productDTOS);
        toolbar = findViewById(R.id.customTopProductBar);

    }

    private void referencesDialogElement(Dialog dialog){
        edtInputQuantity = dialog.findViewById(R.id.edtSellListQuantity);
        btnCloseAddQuantityDialog =dialog.findViewById(R.id.btnCloseSellListDialog);
        btnSaveAddQuantityDialog = dialog.findViewById(R.id.btnAddSellListDialog);
    }

    private void referencesEditDialogElement(Dialog dialog) {
        edtEditProductName = dialog.findViewById(R.id.edtProductNameEdit);
        edtEditProductImportPrice = dialog.findViewById(R.id.edtProductImportPriceEdit);
        edtEditProductSellPrice = dialog.findViewById(R.id.edtProductSellPriceEdit);
        editProductImage = dialog.findViewById(R.id.imgProductEditChoose);
        btnCloseEditProductDialog = dialog.findViewById(R.id.btnCloseProductEditDialog);
        btnEditProductDialog = dialog.findViewById(R.id.btnEditProductDialog);

    }

    private void openAddQuantityDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quantity_dialog);

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

        referencesDialogElement(dialog);

        btnCloseAddQuantityDialog.setOnClickListener(v -> dialog.dismiss());

        //set image from gallery to imageview
        edtInputQuantity.setFilters(new InputFilter[]{new InputFilterMinMax(1, product.getProduct_quantity())});

        btnSaveAddQuantityDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addItemToSellList((int)product.getProduct_id(), Integer.valueOf(edtInputQuantity.getText().toString()));
                Toast.makeText(ListProductActivity.this, "Đã thêm: " + product.getProduct_name()
                        + " vào danh sách", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openEditProductDialog(int gravity){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_product);

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

        referencesEditDialogElement(dialog);

        editProductImage.setImageBitmap(Common.getBitmapFromByteArray(product.getImage()));
        edtEditProductName.setText(product.getProduct_name());
        edtEditProductImportPrice.setText(String.valueOf(product.getImport_price()));
        edtEditProductSellPrice.setText(String.valueOf(product.getSell_price()));

        btnCloseEditProductDialog.setOnClickListener(view -> dialog.dismiss());
        btnEditProductDialog.setOnClickListener(view -> {Toast.makeText(ListProductActivity.this, "từ từ làm", Toast.LENGTH_SHORT).show();});

        dialog.show();
    }



    private void manipulateToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    //set onclick event for topbar menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        } else if (item.getItemId() == R.id.sellStack) {
            Intent it = new Intent(ListProductActivity.this, WaitingListActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }
    //set onclick event to context menu item
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int position = item.getGroupId();

        product = productDTOS.get(position);

        if (item.getItemId() == R.id.context_product_Update) {
            openEditProductDialog(Gravity.CENTER);

        } else if (item.getItemId() == R.id.context_product_Sell) {
            openAddQuantityDialog(Gravity.CENTER);
        } else if (item.getItemId() == R.id.context_product_delete) {
            //deleteProduct();
        }
        return super.onContextItemSelected(item);
    }

    private void addItemToSellList(int id, int quantity){
        SellWaitingList.getInstance(ListProductActivity.this).addProductToSellList(id, quantity);
    }


    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void displayImageForImageView() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {
                            Uri uri = o.getData().getData();
                            editProductImage.setImageURI(uri);
                        } catch (Exception e) {
                            Toast.makeText(ListProductActivity.this, "no image was selected", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void displayListView() {
        productDTOS = databaseHelper.selectAllProduct();
        RecyclerViewProductAdapter productAdapter1 = new RecyclerViewProductAdapter(ListProductActivity.this, productDTOS);
        recyclerView.setAdapter(productAdapter1);
    }
}