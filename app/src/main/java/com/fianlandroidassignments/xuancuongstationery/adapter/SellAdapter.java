package com.fianlandroidassignments.xuancuongstationery.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.Common.InputFilterMinMax;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.activity.WaitingListActivity;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.SellWaitingList;
import com.fianlandroidassignments.xuancuongstationery.dto.SoldBillDetailDTO;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Map;

public class SellAdapter extends BaseAdapter {

    Context context;
    Map<Integer, SoldBillDetailDTO> sellList;

    public SellAdapter(Context context, Map<Integer, SoldBillDetailDTO> sellList) {
        this.context = context;
        this.sellList = sellList;
    }

    @Override
    public int getCount() {
        return sellList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.waiting_sell_list_item, parent, false);

        ShapeableImageView productImg = convertView.findViewById(R.id.sellProductImg);
        TextView productName = convertView.findViewById(R.id.sellProductName);
        TextView sellPrice = convertView.findViewById(R.id.sellProductPrice);
        TextView sellQuantity = convertView.findViewById(R.id.sellProductQuantity);




        sellQuantity.setText("SL: " + sellList.get((int)sellList.keySet().toArray()[position]).getProductQuantity());

        productImg.setImageBitmap
                (
                    Common.getBitmapFromByteArray
                            (DatabaseHelper
                                .getInstance(context)
                                .selectProductById((int)sellList.keySet().toArray()[position]).getImage()
                            )
                );

        productName.setText(DatabaseHelper.getInstance(context)
                .selectProductById((int)sellList.keySet().toArray()[position]).getProduct_name());

        sellPrice.setText("Price: " + String.valueOf(DatabaseHelper.getInstance(context)
                .selectProductById((int)sellList.keySet().toArray()[position]).getSell_price()));



        return convertView;
    }

}
