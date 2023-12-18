package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.CategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.Category;
import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ListView listViewCategory;

    List<CategoryDTO> categoryDTOList;
    MaterialToolbar toolbar;
    FloatingActionButton addNewCateBtn;
    ImageView imgAddCategory, imgEditCategory;
    EditText edtCategoryName, edtEditCategoryName;
    Button btnCloseCategoryDialog, btnSaveToAddNewCategory, btnCloseEditCategoryDialog, btnSaveToEditCategory;
    ActivityResultLauncher<Intent> resultLauncher;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        databaseHelper = new DatabaseHelper(CategoryActivity.this);

        references();
        displayListView();
        manipulateToolbar();

        //set onclick event to add new category button
        addNewCateBtn.setOnClickListener(view -> {
            openAddCategoryDialog(Gravity.CENTER);
        });

        displayImageForImageView();
        displayImageForEditImageView();
        registerForContextMenu(listViewCategory);

    }

    //set toolbar to action bar
    private void manipulateToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //display data to listview
    private void displayListView() {
        categoryDTOList = databaseHelper.selectAllCategory();
        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this, categoryDTOList);
        listViewCategory.setAdapter(categoryAdapter);
    }

    private void references() {
        toolbar = findViewById(R.id.customTopCateBar);
        listViewCategory = findViewById(R.id.listViewCate);
        addNewCateBtn = findViewById(R.id.addNewCateButton);
    }

    private void referencesEditDialogElement(Dialog dialog){
        imgEditCategory = dialog.findViewById(R.id.imgProviderEditChoose);
        edtEditCategoryName = dialog.findViewById(R.id.edtProviderNameEdit);
        btnCloseEditCategoryDialog = dialog.findViewById(R.id.btnCloseProviderEditDialog);
        btnSaveToEditCategory = dialog.findViewById(R.id.btnEditProviderDialog);
    }

    private void referencesDialogElement(Dialog dialog){
        imgAddCategory = dialog.findViewById(R.id.imgCategoryAddChoose);
        edtCategoryName = dialog.findViewById(R.id.edtCategoryNameAdd);
        btnCloseCategoryDialog = dialog.findViewById(R.id.btnCloseCategoryAddDialog);
        btnSaveToAddNewCategory = dialog.findViewById(R.id.btnAddCategoryDialog);
    }

    //create context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.context_menu, menu);
    }

    //set onclick event to context menu item
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        CategoryDTO categoryDTO = categoryDTOList.get(info.position);
        if (item.getItemId() == R.id.context_view)
            Toast.makeText(CategoryActivity.this, "Ban da chon xem o vi tri: "
                    + info.position, Toast.LENGTH_LONG).show();
        else if (item.getItemId() == R.id.context_delete){
            databaseHelper.deleteCategory(categoryDTO.getCategory_id());
            displayListView();
        }
        else if (item.getItemId() == R.id.context_update){
            openEditDialog(Gravity.CENTER, categoryDTO);
        }


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
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        } else if (item.getItemId() == R.id.sellStack) {
            Toast.makeText(CategoryActivity.this, "Chuc nang chua hoan thanh", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAddCategoryDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_category);

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

        btnCloseCategoryDialog.setOnClickListener(v -> dialog.dismiss());

        //set image from gallery to imageview
        imgAddCategory.setOnClickListener(view -> pickImage());

        btnSaveToAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCategory(imgAddCategory, edtCategoryName);

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openEditDialog(int gravity, CategoryDTO categoryDTO) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_provider);

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

        //references dialog view object
        referencesEditDialogElement(dialog);

        edtEditCategoryName.setText(categoryDTO.getCategory_name());
        imgEditCategory.setImageBitmap(Common.getBitmapFromByteArray(categoryDTO.getImage()));

        //close dialog
        btnCloseEditCategoryDialog.setOnClickListener(v -> dialog.dismiss());

        //open gallery to pick image
        imgEditCategory.setOnClickListener(view -> pickImage());

        //insert new provider
        btnSaveToEditCategory.setOnClickListener(view -> {
            if (edtEditCategoryName.getText().toString() == null ||
                    edtEditCategoryName.getText().toString().trim().equals(""))
            {
                Toast.makeText(CategoryActivity.this,"Vui long nhap day du thong tin",Toast.LENGTH_LONG).show();
                return;
            }
            editExistingCategory(categoryDTO.getCategory_id(),imgEditCategory, edtEditCategoryName);
            dialog.dismiss();
        });


        dialog.show();
    }



    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void displayImageForImageView(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try{
                            Uri uri = o.getData().getData();
                            imgAddCategory.setImageURI(uri);
                        }catch (Exception e){
                            Toast.makeText(CategoryActivity.this, "no image was selected",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void displayImageForEditImageView(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try{
                            Uri uri = o.getData().getData();
                            imgEditCategory.setImageURI(uri);
                        }catch (Exception e){
                            Toast.makeText(CategoryActivity.this, "no image was selected",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //insert new category function (is used in "open dialog" function when dialog open and click on add button)
    private void addNewCategory(ImageView imgView, EditText categoryName){

        CategoryDTO categoryDTO =
                new CategoryDTO(categoryName.getText().toString(), Common.convertImageViewToByteArray(imgView));

        long result = databaseHelper.insertNewCategory(categoryDTO);

        if (result != -1){
            Toast.makeText(CategoryActivity.this, "Insert Success", Toast.LENGTH_LONG).show();
            displayListView();
        }
        else
            Toast.makeText(CategoryActivity.this, "Insert Fail", Toast.LENGTH_LONG).show();

    }

    private void editExistingCategory(int oldId, ImageView newImageView, EditText newEditText) {
        CategoryDTO newCategory =
                new CategoryDTO(newEditText.getText().toString(), Common.convertImageViewToByteArray(newImageView));

        int numOfRowAffected = databaseHelper.updateCategory(oldId, newCategory);

        if (numOfRowAffected > 0){
            Toast.makeText(CategoryActivity.this, "edit success", Toast.LENGTH_LONG).show();
            displayListView();
        }else
            Toast.makeText(CategoryActivity.this, "Something wrong!!!", Toast.LENGTH_LONG).show();
    }



}