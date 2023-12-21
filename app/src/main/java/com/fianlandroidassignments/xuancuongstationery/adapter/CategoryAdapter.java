package com.fianlandroidassignments.xuancuongstationery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    Context context;
    List<CategoryDTO> categoryList;

    public CategoryAdapter(Context context, List<CategoryDTO> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        if (categoryList.isEmpty())
            return 0;
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_category, parent, false);

        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryList.get(position).getImage(),
                0,categoryList.get(position).getImage().length);

        ShapeableImageView categoryImage = convertView.findViewById(R.id.listCateImage);
        TextView categoryName = convertView.findViewById(R.id.listCateName);
        TextView quantity = convertView.findViewById(R.id.listCateQuantity);

        categoryImage.setImageBitmap(bitmap);
        //categoryImage.setImageResource(categoryList.get(position).getImage());
        categoryName.setText(categoryList.get(position).getCategory_name());
        quantity.setText("SL: " + categoryList.get(position).getProduct_quantity());

        return convertView;
    }
}
