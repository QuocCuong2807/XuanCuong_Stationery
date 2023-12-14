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
import com.fianlandroidassignments.xuancuongstationery.dto.Provider;

import java.util.ArrayList;
import java.util.List;

public class ArrayProviderAdapter extends ArrayAdapter<Provider> {
    private List<Provider> providerList;

    public ArrayProviderAdapter(@NonNull Context context,  @NonNull List<Provider> providerList) {
        super(context, 0, providerList);
        this.providerList = providerList;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return providerFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_product_exists, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.listItemId);
        Provider providerItem = getItem(position);
        if (providerItem != null) {
            textView.setText(providerItem.getName());
        }

        return convertView;
    }

    private final Filter providerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Provider> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(providerList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Provider item : providerList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            return ((Provider) resultValue).getName();
        }
    };

}
