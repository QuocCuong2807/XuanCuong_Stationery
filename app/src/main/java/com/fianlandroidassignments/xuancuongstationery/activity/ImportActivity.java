package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.dto.Category;
import com.fianlandroidassignments.xuancuongstationery.dto.Provider;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ImportActivity extends AppCompatActivity {
    String[] arrExistsStatus = {"Exists", "Not Exists"};
    AutoCompleteTextView autoCompleteTextViewExistsStatus;
    AutoCompleteTextView autoCompleteTextViewExistsCategory;
    AutoCompleteTextView autoCompleteTextViewExistsProduct;
    TextInputEditText textInputEditTextExistsQuantity;
    Button buttonExistsSave;
    AutoCompleteTextView autoCompleteTextViewNotExistsCategory;
    AutoCompleteTextView autoCompleteTextViewNotExistsProvider;
    TextInputEditText TextInputEditTextNotExistsProduct;
    TextInputEditText TextInputEditTextNotExistsQuantity;
    TextInputEditText TextInputEditTextNotExistsPrice;
    Button buttonNotExistsSave;

    List<Category> categories;
    List<Provider> providers;
    ArrayAdapter<String> adapterExsItems;
    ArrayCategoryAdapter arrayCategoryAdapter;
    ArrayProviderAdapter arrayProviderAdapter;
    RelativeLayout relativeLayoutExisting;
    RelativeLayout relativeLayoutNotExisting;

    ScrollView scrollViewImportProduct;

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        fillCategories();

        fillProviders();

        reference();

        setDefaultBorderColor();

        manipulateToolbar();

        setAdapterForAutoCompleteTextView();



        //handle hide/show import view based on status
        autoCompleteTextViewExistsStatus.setOnItemClickListener((parent, view, position, id) -> {
            if ("Exists".equals(adapterExsItems.getItem(position))) {
                relativeLayoutExisting.setVisibility(View.VISIBLE);
                scrollViewImportProduct.setVisibility(View.INVISIBLE);
            } else if ("Not Exists".equals(adapterExsItems.getItem(position))) {
                scrollViewImportProduct.setVisibility(View.VISIBLE);
                relativeLayoutExisting.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void setAdapterForAutoCompleteTextView(){
        arrayCategoryAdapter = new ArrayCategoryAdapter(this, categories);
        arrayProviderAdapter = new ArrayProviderAdapter(this, providers);
        autoCompleteTextViewNotExistsCategory.setAdapter(arrayCategoryAdapter);
        autoCompleteTextViewExistsCategory.setAdapter(arrayCategoryAdapter);
        autoCompleteTextViewNotExistsProvider.setAdapter(arrayProviderAdapter);


        //set status adapter to status autocompleteTextView
        autoCompleteTextViewExistsStatus.setAdapter(adapterExsItems);
    }

    private void fillCategories(){
        categories  = new ArrayList<>();;
        categories.add(new Category(1, "Book", R.drawable.book, 50));
        categories.add(new Category(2, "Pen", R.drawable.pen, 29));
        categories.add(new Category(3, "Ruler", R.drawable.ruler, 11));
        categories.add(new Category(4, "Eraser", R.drawable.eraser, 6));
        categories.add(new Category(5, "Office Tools", R.drawable.office_tool, 46));
    }

    private void fillProviders(){
        providers = new ArrayList<>();
        providers.add(new Provider(1, "Thiên Long Hoàn Cầu", R.drawable.tlhc));
        providers.add(new Provider(2, "Công ty Mai Son", R.drawable.maison));
        providers.add(new Provider(3, "Công ty VPP Hồng Hà", R.drawable.hongha));
        providers.add(new Provider(4, "Văn phòng phẩm Artline", R.drawable.artline));
    }
    private void reference() {
        autoCompleteTextViewExistsStatus = findViewById(R.id.autoCompleteImportExists);
        autoCompleteTextViewExistsCategory = findViewById(R.id.autoCompleteCategoryList);
        autoCompleteTextViewExistsProduct = findViewById(R.id.autoCompleteProductList);
        textInputEditTextExistsQuantity = findViewById(R.id.txInputEdtQuantity);
        buttonExistsSave = findViewById(R.id.btnImportExistingSave);

        autoCompleteTextViewNotExistsCategory = findViewById(R.id.autoCompleteNotExistingCategoryList);
        autoCompleteTextViewNotExistsProvider = findViewById(R.id.autoCompleteNotExistingProviderList);
        TextInputEditTextNotExistsProduct = findViewById(R.id.txInputEdtNotExistingProductName);
        TextInputEditTextNotExistsQuantity = findViewById(R.id.txInputEdtNotExistingQuantity);
        TextInputEditTextNotExistsPrice = findViewById(R.id.txInputEdtNotExistingPrice);
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