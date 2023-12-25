package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.transition.Transition;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ImportAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.SellAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDetailDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductStatus;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.SellWaitingList;
import com.fianlandroidassignments.xuancuongstationery.dto.SoldBillDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.SoldBillDetailDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.WaitingList;
import com.google.android.material.appbar.MaterialToolbar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WaitingListActivity extends AppCompatActivity {
    private final String[] arrWaitingListStatus = {"Import List", "Sell List"};
    AutoCompleteTextView autoCompleteTextViewWaitingListStatus;
    ArrayAdapter<String> adapterExsItems;
    ListView listViewImport;
    ListView listViewSell;
    RelativeLayout importForm;
    RelativeLayout sellForm;
    ImportAdapter importAdapter;
    SellAdapter sellAdapter;
    Button importBtn, sellBtn, clearImportListBtn, clearSellListBtn;
    MaterialToolbar topNavBar;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_list);

        databaseHelper = new DatabaseHelper(WaitingListActivity.this);

        reference();
        manipulateToolbar();
        setAdapterForAutoCompleteTextView();
        setDefaultBorderColor();

        //handle hide/show import or sell view based on status
        autoCompleteTextViewWaitingListStatus.setOnItemClickListener((parent, view, position, id) -> {
            if ("Import List".equals(adapterExsItems.getItem(position))) {
                importForm.setVisibility(View.VISIBLE);
                sellForm.setVisibility(View.INVISIBLE);
            } else if ("Sell List".equals(adapterExsItems.getItem(position))) {
                sellForm.setVisibility(View.VISIBLE);
                importForm.setVisibility(View.INVISIBLE);
            }
        });

        //set adapter to listview
        setAdapterToListView();

        //set hide/show for btn
        setHideorShowBtn();

        importBtn.setOnClickListener(view -> importNewProduct());
        sellBtn.setOnClickListener(view -> sellProduct());

        clearSellListBtn.setOnClickListener(view -> clearSellList());
        clearImportListBtn.setOnClickListener(view -> clearImportList());

    }

    private void setAdapterToListView(){
        importAdapter = new ImportAdapter(WaitingListActivity.this, WaitingList.importList);
        sellAdapter = new SellAdapter(WaitingListActivity.this,
                SellWaitingList.getInstance(WaitingListActivity.this).getSellWaitingList());

        listViewImport.setAdapter(importAdapter);
        listViewSell.setAdapter(sellAdapter);
    }

    private void setHideorShowBtn(){
        if (WaitingList.importList.isEmpty()) {
            importBtn.setVisibility(View.INVISIBLE);
            clearImportListBtn.setVisibility(View.INVISIBLE);
        }
        else {
            importBtn.setVisibility(View.VISIBLE);
            clearImportListBtn.setVisibility(View.VISIBLE);

        }
        if (SellWaitingList.getInstance(WaitingListActivity.this).getSellWaitingList().isEmpty()) {
            sellBtn.setVisibility(View.INVISIBLE);
            clearSellListBtn.setVisibility(View.INVISIBLE);
        }
        else {
            sellBtn.setVisibility(View.VISIBLE);
            clearSellListBtn.setVisibility(View.VISIBLE);
        }

    }

    private void setDefaultBorderColor(){
        autoCompleteTextViewWaitingListStatus.setDropDownBackgroundResource(R.color.white);

    }

    private void setAdapterForAutoCompleteTextView(){
        //set status adapter to status autocompleteTextView
        autoCompleteTextViewWaitingListStatus.setAdapter(adapterExsItems);

    }

    private void reference() {
        adapterExsItems = new ArrayAdapter<>(this, R.layout.list_item_product_exists, arrWaitingListStatus);
        autoCompleteTextViewWaitingListStatus = findViewById(R.id.autoCompleteImportExists);
        listViewImport = findViewById(R.id.listViewImport);
        listViewSell = findViewById(R.id.listViewSell);
        importForm = findViewById(R.id.importForm);
        sellForm = findViewById(R.id.sellForm);

        importBtn = findViewById(R.id.btnImportSave);
        sellBtn = findViewById(R.id.btnSellSave);
        topNavBar = findViewById(R.id.customTopImportBar);

        clearImportListBtn = findViewById(R.id.btnClearImportList);
        clearSellListBtn = findViewById(R.id.btnClearSellList);
    }

    private void manipulateToolbar() {
        setSupportActionBar(topNavBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    //set onclick event for topbar menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    //import product
    private void importNewProduct(){

        //insert bill to db and select imported bill by id
        ImportBillDTO importBill = new ImportBillDTO(new Date().toString(),0);
        int billId = (int)databaseHelper.insertNewImportBill(importBill);
        ImportBillDTO newImportBill = databaseHelper.selectImportById(billId);

        //insert product and bill detail to db
        for (ProductDTO product: WaitingList.importList
             ) {
            try {
                long productId = databaseHelper.insertNewProduct(product);

                ProductDTO productDTO = databaseHelper.selectProductById((int)productId);
                long importDetailId = databaseHelper.insertNewImportBillDetail(productDTO, newImportBill);

                Toast.makeText(WaitingListActivity.this, "success", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(WaitingListActivity.this, "fail", Toast.LENGTH_LONG).show();
            }
        }

        //update bill total price
        int totalPrice = calTotalBillPrice(databaseHelper.selectAllBillDetailByBillId(billId));
        databaseHelper.updateImportTotalPrice((int)billId, totalPrice);

        //clear import waiting list and redisplay listview
        WaitingList.importList.clear();
        setAdapterToListView();

    }

    private void sellProduct(){
        Map<Integer, SoldBillDetailDTO> sellList
                = SellWaitingList.getInstance(WaitingListActivity.this).getSellWaitingList();


        //insert new sold bill and get new sold bill id
        SoldBillDTO soldBill = new SoldBillDTO(String.valueOf(new Date()), 0);
        long billId =  databaseHelper.insertNewSoldBill(soldBill);
        SoldBillDTO newBill = databaseHelper.selectSoldBillById((int)billId);

        for (Map.Entry<Integer, SoldBillDetailDTO> entry: sellList.entrySet()
             ) {

            //minus product quantity
            long rowAffected = databaseHelper.updateSellProductQuantity(entry.getValue().getProduct().getProduct_id()
                                                            , entry.getValue().getProductQuantity());
            if (rowAffected == 0){
                Toast.makeText(WaitingListActivity.this, "Số lượng mua vượt quá số lượng hiện có", Toast.LENGTH_SHORT).show();
                SellWaitingList.getInstance(WaitingListActivity.this).getSellWaitingList().clear();
                setAdapterToListView();
                return;
            }


            //set new bill and new quantity to bill detail
            entry.getValue().setSoldBill(newBill);

            //insert new bill detail
            long id = databaseHelper.insertSoldBillDetail(entry.getValue());

            if (id != -1)
                Toast.makeText(WaitingListActivity.this, "success", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(WaitingListActivity.this, "fail", Toast.LENGTH_SHORT).show();

        }
        int totalPrice = calTotalSoldBillPrice(databaseHelper.selectAllSoldBillDetailByBillId((int)billId));
        databaseHelper.updateSoldTotalPrice((int)billId, totalPrice);
        SellWaitingList.getInstance(WaitingListActivity.this).getSellWaitingList().clear();
        setAdapterToListView();
    }

    private void clearSellList(){
        SellWaitingList.getInstance(WaitingListActivity.this).getSellWaitingList().clear();
        setAdapterToListView();
    }
    private void clearImportList(){
        WaitingList.importList.clear();
        setAdapterToListView();
    }

    private int calTotalSoldBillPrice(List<SoldBillDetailDTO> soldBillDetailList){
        int sum = 0;
        for (SoldBillDetailDTO soldBillDetailDTO: soldBillDetailList
             ) {
            sum+= soldBillDetailDTO.getSoldPrice();
        }
        return sum;
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
}