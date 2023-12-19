package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProviderAdapter;

public class WaitingListActivity extends AppCompatActivity {
    private final String[] arrWaitingListStatus = {"Import List", "Sell List"};
    AutoCompleteTextView autoCompleteTextViewWaitingListStatus;
    ArrayAdapter<String> adapterExsItems;
    ListView listViewImport;
    ListView listViewSell;

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
                listViewImport.setVisibility(View.VISIBLE);
                listViewSell.setVisibility(View.INVISIBLE);
            } else if ("Sell List".equals(adapterExsItems.getItem(position))) {
                listViewSell.setVisibility(View.VISIBLE);
                listViewImport.setVisibility(View.INVISIBLE);
            }
        });
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
    }
}