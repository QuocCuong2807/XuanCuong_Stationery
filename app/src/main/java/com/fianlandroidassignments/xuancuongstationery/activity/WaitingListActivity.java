package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ImportAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDetailDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductStatus;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.WaitingList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class WaitingListActivity extends AppCompatActivity {
    private final String[] arrWaitingListStatus = {"Import List", "Sell List"};
    AutoCompleteTextView autoCompleteTextViewWaitingListStatus;
    ArrayAdapter<String> adapterExsItems;
    ListView listViewImport;
    ListView listViewSell;
    RelativeLayout importForm;
    RelativeLayout sellForm;
    ImportAdapter importAdapter;
    Button importBtn, sellBtn;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_list);

        databaseHelper = new DatabaseHelper(WaitingListActivity.this);

        reference();
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
    }

    private void setAdapterToListView(){
        importAdapter = new ImportAdapter(WaitingListActivity.this, WaitingList.importList);
        listViewImport.setAdapter(importAdapter);
    }

    private void setHideorShowBtn(){
        if (WaitingList.importList.isEmpty())
            importBtn.setVisibility(View.INVISIBLE);
        else
            importBtn.setVisibility(View.VISIBLE);

        if (WaitingList.sellList.isEmpty())
            sellBtn.setVisibility(View.INVISIBLE);
        else
            sellBtn.setVisibility(View.VISIBLE);

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