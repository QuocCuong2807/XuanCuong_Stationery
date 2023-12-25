package com.fianlandroidassignments.xuancuongstationery.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDTO;

import java.util.List;

public class RecyclerViewImportBillAdapter extends RecyclerView.Adapter<RecyclerViewImportBillAdapter.MyViewHolder> {
    Context context;
    List<ImportBillDTO> importBillDTOS;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public RecyclerViewImportBillAdapter(Context context, List<ImportBillDTO> importBillDTOS) {
        this.context = context;
        this.importBillDTOS = importBillDTOS;
    }

    @NonNull
    @Override
    public RecyclerViewImportBillAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_bill, parent, false);

        return new RecyclerViewImportBillAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewImportBillAdapter.MyViewHolder holder, int position) {
        holder.tvImportBillId.setText(String.valueOf(importBillDTOS.get(position).getBillId()));
        holder.tvImportBillDate.setText(String.valueOf(importBillDTOS.get(position).getDate()));
        holder.tvImportBillTotal.setText(importBillDTOS.get(position).getTotalPrice() + " VND");

    }

    @Override
    public int getItemCount() {
        return importBillDTOS.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvImportBillId;
        TextView tvImportBillDate;
        TextView tvImportBillTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvImportBillId = itemView.findViewById(R.id.tvRVBillId);
            tvImportBillDate = itemView.findViewById(R.id.tvRVBillDate);
            tvImportBillTotal = itemView.findViewById(R.id.tvRVBillTotal);
        }
    }
}
