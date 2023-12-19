package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductStatus;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.WaitingList;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ImportActivity extends AppCompatActivity {
    private final String[] arrExistsStatus = {"Available", "New product"};
    AutoCompleteTextView autoCompleteTextViewExistsStatus;
    AutoCompleteTextView autoCompleteTextViewExistsCategory;
    AutoCompleteTextView autoCompleteTextViewExistsProduct;
    TextInputEditText textInputEditTextExistsQuantity;
    Button buttonExistsSave;
    AutoCompleteTextView autoCompleteTextViewNotExistsCategory;
    AutoCompleteTextView autoCompleteTextViewNotExistsProvider;
    TextInputEditText TextInputEditTextNotExistsProduct;
    TextInputEditText TextInputEditTextNotExistsQuantity;
    TextInputEditText TextInputEditTextNotExistsPriceImport;
    TextInputEditText TextInputEditTextNotExistsPriceSell;
    Button buttonNotExistsSave;
    List<CategoryDTO> categoryDTOList;
    List<ProviderDTO> providers;
    ArrayAdapter<String> adapterExsItems;
    ArrayCategoryAdapter arrayCategoryAdapter;
    ArrayProviderAdapter arrayProviderAdapter;
    RelativeLayout relativeLayoutExisting;
    RelativeLayout relativeLayoutNotExisting;

    ScrollView scrollViewImportProduct;

    MaterialToolbar toolbar;

    DatabaseHelper databaseHelper;
    ImageView imgNewImport, imgExistingImport;
    ActivityResultLauncher<Intent> resultLauncher;
    private ProviderDTO newProvider;
    private CategoryDTO newCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        databaseHelper = new DatabaseHelper(ImportActivity.this);

        fillCategories();

        fillProviders();

        reference();

        setDefaultBorderColor();

        manipulateToolbar();

        setAdapterForAutoCompleteTextView();



        //handle hide/show import view based on status
        autoCompleteTextViewExistsStatus.setOnItemClickListener((parent, view, position, id) -> {
            if ("Available".equals(adapterExsItems.getItem(position))) {
                relativeLayoutExisting.setVisibility(View.VISIBLE);
                scrollViewImportProduct.setVisibility(View.INVISIBLE);
            } else if ("New product".equals(adapterExsItems.getItem(position))) {
                scrollViewImportProduct.setVisibility(View.VISIBLE);
                relativeLayoutExisting.setVisibility(View.INVISIBLE);
            }
        });

        imgNewImport.setOnClickListener(view -> pickImageForNewImport());

        displayImageForNewImport();

        buttonNotExistsSave.setOnClickListener(view -> addNewProductToWaitingList());

    }

    private void addNewProductToWaitingList(){

        //get category and provider from autocomplete textview
        autoCompleteTextViewNotExistsProvider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newProvider = providers.get(position);
            }
        });

        autoCompleteTextViewNotExistsCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newCategory = categoryDTOList.get(position);
            }
        });

        String productName = TextInputEditTextNotExistsProduct.getText().toString();
        int quantity = Integer.valueOf(TextInputEditTextNotExistsQuantity.getText().toString());
        int importPrice = Integer.valueOf(TextInputEditTextNotExistsPriceImport.getText().toString());
        int sellPrice = Integer.valueOf(TextInputEditTextNotExistsPriceSell.getText().toString());
        byte[] productImg = Common.convertImageViewToByteArray(imgNewImport);

        ProductDTO productDTO = new ProductDTO(productName,productImg,quantity,importPrice,sellPrice
                    , ProductStatus.AVAILABLE.getValue(), "",newCategory,newProvider);

        int size = WaitingList.importList.size();
        WaitingList.importList.add(productDTO);


        if(size >= WaitingList.importList.size())
            Toast.makeText(ImportActivity.this, "fail",Toast.LENGTH_LONG).show();
        else{
            Toast.makeText(ImportActivity.this, "success",Toast.LENGTH_LONG).show();

            TextInputEditTextNotExistsProduct.setText("");
            TextInputEditTextNotExistsQuantity.setText("");
            TextInputEditTextNotExistsPriceImport.setText("");
            TextInputEditTextNotExistsPriceSell.setText("");
            imgNewImport.setImageResource(R.drawable.gallery);
        }

    }

    private void setAdapterForAutoCompleteTextView(){
        arrayCategoryAdapter = new ArrayCategoryAdapter(this, categoryDTOList);
        arrayProviderAdapter = new ArrayProviderAdapter(this, providers);
        autoCompleteTextViewNotExistsCategory.setAdapter(arrayCategoryAdapter);
        autoCompleteTextViewExistsCategory.setAdapter(arrayCategoryAdapter);
        autoCompleteTextViewNotExistsProvider.setAdapter(arrayProviderAdapter);

        //set status adapter to status autocompleteTextView
        autoCompleteTextViewExistsStatus.setAdapter(adapterExsItems);

    }

    private void fillCategories(){
        categoryDTOList = databaseHelper.selectAllCategory();
    }

    private void fillProviders(){
        providers = databaseHelper.selectAllProvider();

    }
    private void reference() {
        autoCompleteTextViewExistsStatus = findViewById(R.id.autoCompleteImportExists);
        autoCompleteTextViewExistsCategory = findViewById(R.id.autoCompleteCategoryList);
        autoCompleteTextViewExistsProduct = findViewById(R.id.autoCompleteProductList);
        textInputEditTextExistsQuantity = findViewById(R.id.txInputEdtQuantity);
        imgExistingImport = findViewById(R.id.imgExistingChoose);
        buttonExistsSave = findViewById(R.id.btnImportExistingSave);

        autoCompleteTextViewNotExistsCategory = findViewById(R.id.autoCompleteNotExistingCategoryList);
        autoCompleteTextViewNotExistsProvider = findViewById(R.id.autoCompleteNotExistingProviderList);
        TextInputEditTextNotExistsProduct = findViewById(R.id.txInputEdtNotExistingProductName);
        TextInputEditTextNotExistsQuantity = findViewById(R.id.txInputEdtNotExistingQuantity);
        TextInputEditTextNotExistsPriceImport = findViewById(R.id.txInputEdtNotExistingPriceImport);
        TextInputEditTextNotExistsPriceSell = findViewById(R.id.txInputEdtNotExistingPriceSell);
        imgNewImport = findViewById(R.id.imgNewChoose);
        buttonNotExistsSave = findViewById(R.id.btnImportNotExistingSave);

        adapterExsItems = new ArrayAdapter<>(this, R.layout.list_item_product_exists, arrExistsStatus);
        relativeLayoutExisting = findViewById(R.id.existingForm);
        relativeLayoutNotExisting = findViewById(R.id.notExistingForm);
        scrollViewImportProduct = findViewById(R.id.scrollViewNotExisting);

        toolbar = findViewById(R.id.customTopImportBar);
    }

    private void setDefaultBorderColor(){
        autoCompleteTextViewExistsStatus.setDropDownBackgroundResource(R.color.white);
        autoCompleteTextViewExistsCategory.setDropDownBackgroundResource(R.color.white);
        autoCompleteTextViewExistsProduct.setDropDownBackgroundResource(R.color.white);
        autoCompleteTextViewNotExistsCategory.setDropDownBackgroundResource(R.color.white);
        autoCompleteTextViewNotExistsProvider.setDropDownBackgroundResource(R.color.white);
    }

    private void manipulateToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void pickImageForNewImport(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void displayImageForNewImport(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try{
                            Uri uri = o.getData().getData();
                            imgNewImport.setImageURI(uri);
                        }catch (Exception e){
                            Toast.makeText(ImportActivity.this, "no image was selected",Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
            Toast.makeText(ImportActivity.this, "Chuc nang chua hoan thanh", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

}