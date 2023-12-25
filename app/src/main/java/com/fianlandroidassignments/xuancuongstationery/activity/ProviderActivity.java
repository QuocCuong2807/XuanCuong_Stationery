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
import android.graphics.BitmapFactory;
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
import com.fianlandroidassignments.xuancuongstationery.adapter.ProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ProviderActivity extends AppCompatActivity {

    ListView providerListView;
    List<ProviderDTO> providers;
    MaterialToolbar toolbar;
    FloatingActionButton addNewProviderBtn;
    ImageView imgAddProvider, imgEditProvider;
    EditText edtProviderName, edtProviderEditName;
    Button btnCloseProviderDialog, btnCloseEditProviderDialog;
    Button btnSaveToAddNewProvider, btnSaveToEditProvider;
    ActivityResultLauncher<Intent> resultLauncher, resultLauncherEdit;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        //initialization DatabaseHelper object
        databaseHelper = new DatabaseHelper(ProviderActivity.this);

        references();
        displayListView();
        manipulateToolbar();


        //set onclick event to add new provider button
        addNewProviderBtn.setOnClickListener(view -> {
            openAddProviderDialog(Gravity.CENTER);
        });

        //display image from gallery to dialog's ImageView
        displayImageForAddImageView();
        displayImageForEditImageView();

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

        //get all provider
        providers = databaseHelper.selectAllProvider();

        //initialize provider adapter and set adapter to listview
        ProviderAdapter providerAdapter = new ProviderAdapter(this, providers);
        providerListView.setAdapter(providerAdapter);
    }

    private void references() {
        toolbar = findViewById(R.id.customTopProviderBar);
        providerListView = findViewById(R.id.listViewProvider);
        addNewProviderBtn = findViewById(R.id.addNewProviderButton);
    }

    private void referencesEditDialogElement(Dialog dialog){
        imgEditProvider = dialog.findViewById(R.id.imgProviderEditChoose);
        edtProviderEditName = dialog.findViewById(R.id.edtProviderNameEdit);
        btnCloseEditProviderDialog = dialog.findViewById(R.id.btnCloseProviderEditDialog);
        btnSaveToEditProvider = dialog.findViewById(R.id.btnEditProviderDialog);
    }

    private void referencesAddDialogElement(Dialog dialog){
        imgAddProvider = dialog.findViewById(R.id.imgProviderAddChoose);
        edtProviderName = dialog.findViewById(R.id.edtProviderNameAdd);
        btnCloseProviderDialog = dialog.findViewById(R.id.btnCloseProviderAddDialog);
        btnSaveToAddNewProvider = dialog.findViewById(R.id.btnAddProviderDialog);
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

        //get provider object by list index
        ProviderDTO providerDTO = providers.get(info.position);

        if (item.getItemId() == R.id.context_view)
            Toast.makeText(ProviderActivity.this, "Ban da chon xem o vi tri: "
                    + info.position, Toast.LENGTH_LONG).show();
        else if (item.getItemId() == R.id.context_delete){

            long numOfRow = databaseHelper.deleteProvider(providerDTO.getId());
            if (numOfRow > 0)
                Toast.makeText(ProviderActivity.this, "success "
                        + info.position, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ProviderActivity.this, "false "
                        + info.position, Toast.LENGTH_LONG).show();
            displayListView();
        }
        else if (item.getItemId() == R.id.context_update)
            openEditDialog(Gravity.CENTER, providerDTO);

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
            Intent it = new Intent(ProviderActivity.this, WaitingListActivity.class);
            startActivity(it);
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

        //references dialog view object
        referencesAddDialogElement(dialog);


        //close dialog
        btnCloseProviderDialog.setOnClickListener(v -> dialog.dismiss());

        //open gallery to pick image
        imgAddProvider.setOnClickListener(view -> pickImageForAdd());

        //insert new provider
        btnSaveToAddNewProvider.setOnClickListener(view -> {
            if (edtProviderName.getText().toString() == null || edtProviderName.getText().toString().trim().equals(""))
            {
                Toast.makeText(ProviderActivity.this,"Vui long nhap day du thong tin",Toast.LENGTH_LONG).show();
                return;
            }
            addNewProvider(imgAddProvider,edtProviderName);
            dialog.dismiss();
        });


        dialog.show();
    }


    private void openEditDialog(int gravity, ProviderDTO providerDTO) {
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

        edtProviderEditName.setText(providerDTO.getName());
        imgEditProvider.setImageBitmap(Common.getBitmapFromByteArray(providerDTO.getImage()));

        //close dialog
        btnCloseEditProviderDialog.setOnClickListener(v -> dialog.dismiss());

        //open gallery to pick image
        imgEditProvider.setOnClickListener(view -> pickImageForEdit());

        //insert new provider
        btnSaveToEditProvider.setOnClickListener(view -> {
            if (edtProviderEditName.getText().toString() == null ||
                                        edtProviderEditName.getText().toString().trim().equals(""))
            {
                Toast.makeText(ProviderActivity.this,"Vui long nhap day du thong tin",Toast.LENGTH_LONG).show();
                return;
            }
            editExistingProvider(providerDTO.getId(),imgEditProvider, edtProviderEditName);
            dialog.dismiss();
        });


        dialog.show();
    }

    //insert new provider function (is used in "open dialog" function when dialog open and click on add button)
    private void addNewProvider(ImageView imgView, EditText providerName){

        ProviderDTO providerDTO = new ProviderDTO(providerName.getText().toString(), Common.convertImageViewToByteArray(imgView));
        databaseHelper = new DatabaseHelper(ProviderActivity.this);

        //return new product id
        long newProductId = databaseHelper.insertNewProvider(providerDTO);

        if (newProductId != -1){
            Toast.makeText(ProviderActivity.this, "insert success", Toast.LENGTH_LONG).show();
            displayListView();
        }
        else
            Toast.makeText(ProviderActivity.this, "Something wrong!!!", Toast.LENGTH_LONG).show();

    }

    //edit existing provider function (is used in "open edit dialog" function when dialog open and click on edit button)
    private void editExistingProvider(int oldId, ImageView newImageView, EditText newEditText){
        ProviderDTO newProvider =
                new ProviderDTO(newEditText.getText().toString(), Common.convertImageViewToByteArray(newImageView));

        int numOfRowAffected = databaseHelper.updateProvider(oldId, newProvider);

        if (numOfRowAffected > 0){
            Toast.makeText(ProviderActivity.this, "edit success", Toast.LENGTH_LONG).show();
            displayListView();
        }else
            Toast.makeText(ProviderActivity.this, "Something wrong!!!", Toast.LENGTH_LONG).show();
    }

    //pick image from gallery
    private void pickImageForAdd(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void pickImageForEdit(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncherEdit.launch(intent);
    }
    //display image to ImageView
    private void displayImageForAddImageView(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try{
                            Uri uri = o.getData().getData();
                            imgAddProvider.setImageURI(uri);
                        }catch (Exception e){
                            Toast.makeText(ProviderActivity.this, "no image was selected",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //display image from gallery to image view in edit dialog
    private void displayImageForEditImageView(){
        resultLauncherEdit = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try{
                            Uri uri = o.getData().getData();
                            imgEditProvider.setImageURI(uri);
                        }catch (Exception e){
                            Toast.makeText(ProviderActivity.this, "no image was selected",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}