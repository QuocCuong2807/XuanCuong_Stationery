package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindow;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProductAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDetailDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductStatus;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.WaitingList;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportActivity extends AppCompatActivity {
    private final String[] arrExistsStatus = {"Available", "New product"};
    AutoCompleteTextView autoCompleteTextViewExistsStatus;
    AutoCompleteTextView autoCompleteTextViewExistsCategory;
    AutoCompleteTextView autoCompleteTextViewExistsProduct;
    TextInputEditText textInputEditTextExistsQuantity;
    AutoCompleteTextView autoCompleteTextViewNotExistsCategory;
    AutoCompleteTextView autoCompleteTextViewNotExistsProvider;
    TextView autoCompleteTextViewItem;
    TextInputEditText TextInputEditTextNotExistsProduct;
    TextInputEditText TextInputEditTextNotExistsQuantity;
    TextInputEditText TextInputEditTextNotExistsPriceImport;
    TextInputEditText TextInputEditTextNotExistsPriceSell;
    Button buttonNotExistsSave, buttonExistsSave;
    List<CategoryDTO> categoryDTOList;
    List<ProviderDTO> providers;
    List<ProductDTO> products;
    ArrayAdapter<String> adapterExsItems;
    ArrayCategoryAdapter arrayCategoryAdapter;
    ArrayProviderAdapter arrayProviderAdapter;
    ArrayProductAdapter arrayProductAdapter;
    RelativeLayout relativeLayoutExisting;
    RelativeLayout relativeLayoutNotExisting;

    ScrollView scrollViewImportProduct;

    MaterialToolbar toolbar;

    DatabaseHelper databaseHelper;
    ImageView imgNewImport, imgExistingImport;
    ActivityResultLauncher<Intent> resultLauncher;
    private ProviderDTO newProvider;
    private CategoryDTO newCategory, existingCategory;
    private ProductDTO productDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 *1024);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        autoCompleteTextViewNotExistsProvider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newProvider = providers.get(position);
            }
        });

        autoCompleteTextViewNotExistsCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newCategory  = categoryDTOList.get(position);
            }
        });

        buttonNotExistsSave.setOnClickListener(view -> addNewProductToWaitingList());

        autoCompleteTextViewExistsCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                existingCategory = categoryDTOList.get(position);
                fillProducts(existingCategory.getCategory_id());
                arrayProductAdapter = new ArrayProductAdapter(ImportActivity.this, products);
                autoCompleteTextViewExistsProduct.setAdapter(arrayProductAdapter);
            }
        });
        autoCompleteTextViewExistsProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productDTO = products.get(position);
            }
        });


        buttonExistsSave.setOnClickListener(view -> addExistingProduct(productDTO));
    }

    private void addNewProductToWaitingList(){

        //get category and provider from autocomplete textview

        String productName = TextInputEditTextNotExistsProduct.getText().toString();
        int quantity = Integer.valueOf(TextInputEditTextNotExistsQuantity.getText().toString());
        int importPrice = Integer.valueOf(TextInputEditTextNotExistsPriceImport.getText().toString());
        int sellPrice = Integer.valueOf(TextInputEditTextNotExistsPriceSell.getText().toString());
        byte[] productImg = Common.convertImageViewToByteArray(imgNewImport);

        boolean isValid = validateAddNewInput(TextInputEditTextNotExistsProduct,TextInputEditTextNotExistsQuantity
                , TextInputEditTextNotExistsPriceImport, TextInputEditTextNotExistsPriceSell,
                autoCompleteTextViewNotExistsCategory, autoCompleteTextViewNotExistsProvider);


        //validate input before initiate object
        if (!isValid){
            Toast.makeText(ImportActivity.this, "Khong duoc de trong thong tin", Toast.LENGTH_LONG).show();

        }else{
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
                autoCompleteTextViewNotExistsCategory.setText("");
                autoCompleteTextViewNotExistsProvider.setText("");
                imgNewImport.setImageResource(R.drawable.gallery);
                setAdapterForAutoCompleteTextView();
            }
        }

    }

    private boolean validateAddNewInput(EditText name, EditText quantity, EditText importPrice
            ,EditText sellPrice, AutoCompleteTextView category, AutoCompleteTextView provider)
    {

        String text = category.getText().toString();
        if (name.getText().toString().trim().equals("") || name == null)
            return false;
        if (quantity.getText().toString().trim().equals("") || quantity == null
                || quantity.getText().toString().trim().equals("0"))
            return false;
        if (importPrice.getText().toString().trim().equals("") || importPrice == null
                || importPrice.getText().toString().trim().equals("0"))
            return false;
        if (sellPrice.getText().toString().trim().equals("") || sellPrice == null
                || sellPrice.getText().toString().trim().equals("0"))
            return false;
        if (category.getText().toString().trim().equals("") || category.getText().toString() == null)
            return false;
        if (provider.getText().toString().trim().equals("") || provider.getText().toString() == null)
            return false;


        return true;
    }


    private boolean validateExistedAddingInput(AutoCompleteTextView category, AutoCompleteTextView product, EditText quantity){

        if (quantity.getText().toString().trim().equals("") || quantity == null
                || quantity.getText().toString().trim().equals("0"))
            return false;
        if (category.getText().toString().trim().equals("") || category == null)
            return false;
        if (product.getText().toString().trim().equals("") || product == null)
            return false;
        return true;
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

    private void fillProducts(int id){
        products = databaseHelper.selectProductByCateId(id);
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


        /*providers = new ArrayList<>();
        categoryDTOList = new ArrayList<>();*/

        toolbar = findViewById(R.id.customTopImportBar);
        /*newProvider = new ProviderDTO();
        newCategory = new CategoryDTO();*/
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


    private void addExistingProduct(ProductDTO existingProduct){
        boolean isValid =
        validateExistedAddingInput(autoCompleteTextViewExistsCategory, autoCompleteTextViewExistsProduct, textInputEditTextExistsQuantity);

        int quantity = textInputEditTextExistsQuantity.getText().toString().trim().equals("") ? 0
                : Integer.valueOf(textInputEditTextExistsQuantity.getText().toString());

        //validate input
        if (!isValid)
            Toast.makeText(ImportActivity.this, "vui long nhap du thong tin", Toast.LENGTH_LONG).show();
        else{
            //update existing product quantity, insert new import bill
            try {
                ImportBillDTO importBillDTO = new ImportBillDTO(new Date().toString(), 0);
                long billId = databaseHelper.insertNewImportBill(importBillDTO);
                ImportBillDTO newImportBill = databaseHelper.selectImportById((int)billId);

                int rowAffected = databaseHelper.updateProductQuantity(existingProduct.getProduct_id(), quantity);

                if (billId != -1 && rowAffected > 0){
                    long importDetailId = databaseHelper.insertNewImportBillDetail(productDTO, newImportBill);
                    if (importDetailId != -1){
                        int totalPrice = calTotalBillPrice(databaseHelper.selectAllBillDetailByBillId((int)billId));
                        databaseHelper.updateImportTotalPrice((int)billId, totalPrice);
                    }
                    Toast.makeText(ImportActivity.this, "success", Toast.LENGTH_LONG).show();
                    autoCompleteTextViewExistsCategory.setText("");
                    autoCompleteTextViewExistsProduct.setText("");
                    textInputEditTextExistsQuantity.setText("");
                }
            }catch (Exception e){
                Toast.makeText(ImportActivity.this, "fail", Toast.LENGTH_LONG).show();

            }
        }
    }

    //calculating total price in bill table
    private int calTotalBillPrice(List<ImportBillDetailDTO> billDetailDTOList){
        int sum = 0;
        for (ImportBillDetailDTO item: billDetailDTOList
        ) {
            sum += item.getImportPrice();
        }
        return sum;
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
            Intent it = new Intent(ImportActivity.this, WaitingListActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

}