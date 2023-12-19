package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ImportAdapter;
import com.fianlandroidassignments.xuancuongstationery.dto.WaitingList;

public class WaitingListActivity extends AppCompatActivity {
    private final String[] arrWaitingListStatus = {"Import List", "Sell List"};
    AutoCompleteTextView autoCompleteTextViewWaitingListStatus;
    ArrayAdapter<String> adapterExsItems;
    ListView listViewImport;
    ListView listViewSell;
    RelativeLayout importForm;
    RelativeLayout sellForm;
    ImportAdapter importAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_list);

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
    }

    private void setAdapterToListView(){
        importAdapter = new ImportAdapter(WaitingListActivity.this, WaitingList.importList);
        listViewImport.setAdapter(importAdapter);
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
    }
}