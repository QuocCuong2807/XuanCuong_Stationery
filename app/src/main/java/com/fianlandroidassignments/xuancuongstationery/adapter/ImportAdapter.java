package com.fianlandroidassignments.xuancuongstationery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;

import java.util.List;

public class ImportAdapter extends BaseAdapter {
    Context context;
    List<ProductDTO> productList;

    public ImportAdapter(Context context, List<ProductDTO> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        if (productList.isEmpty())
            return 0;
        return productList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.waiting_list_item,parent, false);

        ImageView productImg = convertView.findViewById(R.id.importedProductImg);
        TextView productName =convertView.findViewById(R.id.importedProductName);
        TextView quantity = convertView.findViewById(R.id.importedProductQuantity);
        TextView price = convertView.findViewById(R.id.importedProductPrice);

        productImg.setImageBitmap(Common.getBitmapFromByteArray(productList.get(position).getImage()));
        productName.setText(productList.get(position).getProduct_name());
        quantity.setText("SL: " + productList.get(position).getProduct_quantity());
        price.setText("Price: " + productList.get(position).getSell_price() + " VND");

        return convertView;
    }
}
