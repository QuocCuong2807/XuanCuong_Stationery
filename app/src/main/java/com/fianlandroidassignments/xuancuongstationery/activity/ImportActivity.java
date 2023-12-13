package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.dto.Category;
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
    ArrayAdapter<String> adapterExsItems;
    ArrayCategoryAdapter arrayCategoryAdapter;
    RelativeLayout relativeLayoutExisting;
    RelativeLayout relativeLayoutNotExisting;

    ScrollView scrollViewImportProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        fillCategoryList();
        reference();

        setDefaultBorderColor();

        arrayCategoryAdapter = new ArrayCategoryAdapter(this, categories);
        autoCompleteTextViewExistsCategory.setAdapter(arrayCategoryAdapter);

        autoCompleteTextViewExistsStatus.setAdapter(adapterExsItems);



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


    private void fillCategoryList(){
        categories  = new ArrayList<>();;
        categories.add(new Category(1, "Book", R.drawable.book, 50));
        categories.add(new Category(2, "Pen", R.drawable.pen, 29));
        categories.add(new Category(3, "Ruler", R.drawable.ruler, 11));
        categories.add(new Category(4, "Eraser", R.drawable.eraser, 6));
        categories.add(new Category(5, "Office Tools", R.drawable.office_tool, 46));
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
    }

    private void setDefaultBorderColor(){
        autoCompleteTextViewExistsStatus.setDropDownBackgroundResource(R.color.white);
        autoCompleteTextViewExistsCategory.setDropDownBackgroundResource(R.color.white);
        autoCompleteTextViewExistsProduct.setDropDownBackgroundResource(R.color.white);
        autoCompleteTextViewNotExistsCategory.setDropDownBackgroundResource(R.color.white);
        autoCompleteTextViewNotExistsProvider.setDropDownBackgroundResource(R.color.white);
    }

}