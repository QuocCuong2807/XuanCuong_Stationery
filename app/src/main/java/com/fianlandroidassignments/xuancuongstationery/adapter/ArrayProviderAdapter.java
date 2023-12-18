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
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;

import java.util.ArrayList;
import java.util.List;

public class ArrayProviderAdapter extends ArrayAdapter<ProviderDTO> {
    private List<ProviderDTO> providerList;

    public ArrayProviderAdapter(@NonNull Context context,  @NonNull List<ProviderDTO> providerList) {
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
        ProviderDTO providerItem = getItem(position);
        if (providerItem != null) {
            textView.setText(providerItem.getName());
        }

        return convertView;
    }

    private final Filter providerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ProviderDTO> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(providerList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProviderDTO item : providerList) {
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
            return ((ProviderDTO) resultValue).getName();
        }
    };

}
