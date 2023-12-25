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
import com.fianlandroidassignments.xuancuongstationery.dto.SoldBillDTO;

import java.util.List;

public class RecyclerViewSoldBillAdapter extends RecyclerView.Adapter<RecyclerViewSoldBillAdapter.MyViewHolder> {
    Context context;
    List<SoldBillDTO> soldBillDTOS;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public RecyclerViewSoldBillAdapter(Context context, List<SoldBillDTO> soldBillDTOS) {
        this.context = context;
        this.soldBillDTOS = soldBillDTOS;
    }

    @NonNull
    @Override
    public RecyclerViewSoldBillAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_bill, parent, false);

        return new RecyclerViewSoldBillAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSoldBillAdapter.MyViewHolder holder, int position) {
        holder.tvSoldBillId.setText(String.valueOf(soldBillDTOS.get(position).getBillId()));
        holder.tvSoldBillDate.setText(String.valueOf(soldBillDTOS.get(position).getDate()));
        holder.tvSoldBillTotal.setText(soldBillDTOS.get(position).getTotalPrice() + " VND");

    }

    @Override
    public int getItemCount() {
        return soldBillDTOS.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSoldBillId;
        TextView tvSoldBillDate;
        TextView tvSoldBillTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSoldBillId = itemView.findViewById(R.id.tvRVBillId);
            tvSoldBillDate = itemView.findViewById(R.id.tvRVBillDate);
            tvSoldBillTotal = itemView.findViewById(R.id.tvRVBillTotal);
        }
    }
}
