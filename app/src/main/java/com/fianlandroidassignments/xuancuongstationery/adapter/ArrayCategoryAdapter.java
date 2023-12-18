package com.fianlandroidassignments.xuancuongstationery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.dto.Category;
import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;

import java.util.ArrayList;
import java.util.List;

public class ArrayCategoryAdapter extends ArrayAdapter<CategoryDTO> {
    private List<CategoryDTO> categoryListFull;
    public ArrayCategoryAdapter(@NonNull Context context,  @NonNull List<CategoryDTO> categoryList) {
        super(context, 0, categoryList);
        this.categoryListFull = categoryList;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return categoryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_product_exists, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.listItemId);
        CategoryDTO categoryItem = getItem(position);
        if (categoryItem != null) {
            textView.setText(categoryItem.getCategory_name());
        }

        return convertView;
    }

    private final Filter categoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<CategoryDTO> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(categoryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CategoryDTO item : categoryListFull) {
                    if (item.getCategory_name().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((CategoryDTO) resultValue).getCategory_name();
        }
    };
}
