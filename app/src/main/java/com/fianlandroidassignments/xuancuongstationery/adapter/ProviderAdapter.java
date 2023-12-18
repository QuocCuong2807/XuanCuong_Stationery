package com.fianlandroidassignments.xuancuongstationery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ProviderAdapter extends BaseAdapter {

    Context context;
    List<ProviderDTO> providerList;

    public ProviderAdapter(Context context, List<ProviderDTO> providerList) {
        this.context = context;
        this.providerList = providerList;
    }

    @Override
    public int getCount() {
        if(providerList.isEmpty())
            return 0;
        return providerList.size();
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

        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_provider, parent, false);

        Bitmap bitmap = Common.getBitmapFromByteArray(providerList.get(position).getImage());

        ShapeableImageView providerImage = convertView.findViewById(R.id.listProviderImage);
        TextView providerName = convertView.findViewById(R.id.listProviderName);

        providerImage.setImageBitmap(bitmap);
        providerName.setText(providerList.get(position).getName());

        return convertView;
    }
}
