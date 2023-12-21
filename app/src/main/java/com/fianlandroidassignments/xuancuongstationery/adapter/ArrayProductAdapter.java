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
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;

import java.util.ArrayList;
import java.util.List;

public class ArrayProductAdapter extends ArrayAdapter<ProductDTO> {
    List<ProductDTO> productDTOList;

    public ArrayProductAdapter(@NonNull Context context, @NonNull List<ProductDTO> productDTOList) {
        super(context, 0, productDTOList);
        this.productDTOList = productDTOList;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return productFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_product_exists, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.listItemId);
        ProductDTO productItem = getItem(position);
        if (productItem != null) {
            textView.setText(productItem.getProduct_name());
        }

        return convertView;
    }

    private final Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ProductDTO> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(productDTOList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProductDTO item : productDTOList) {
                    if (item.getProduct_name().toLowerCase().contains(filterPattern)) {
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
            return ((ProductDTO) resultValue).getProduct_name();
        }
    };

}
