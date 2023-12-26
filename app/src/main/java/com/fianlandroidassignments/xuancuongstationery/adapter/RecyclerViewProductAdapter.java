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
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;

import java.util.List;

public class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductAdapter.MyViewHolder>
        implements View.OnCreateContextMenuListener {
    Context context;
    List<ProductDTO> productDTOS;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public RecyclerViewProductAdapter(Context context, List<ProductDTO> productDTOS) {
        this.context = context;
        this.productDTOS = productDTOS;
    }

    @NonNull
    @Override
    public RecyclerViewProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_product, parent, false);

        return new RecyclerViewProductAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewProductAdapter.MyViewHolder holder, int position) {
        holder.imgProduct.setImageBitmap(Common.getBitmapFromByteArray(productDTOS.get(position).getImage()));

        holder.tvProductName.setText(productDTOS.get(position).getProduct_name());
        holder.tvProductImportPrice.setText("Import Price: " + Common.getCurrencyFormat()
                                        .format(productDTOS.get(position).getImport_price()) + " VND");
        holder.tvProductSellPrice.setText("Sell Price: " + Common.getCurrencyFormat()
                                        .format(productDTOS.get(position).getSell_price()) + " VND");

        holder.tvProductQuantity.setText("Quantity: " + productDTOS.get(position).getProduct_quantity());

    }

    @Override
    public int getItemCount() {
        return productDTOS.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageView imgProduct;
        TextView tvProductName;
        TextView tvProductImportPrice;
        TextView tvProductSellPrice;
        TextView tvProductQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductImportPrice = itemView.findViewById(R.id.tvProductImportPrice);
            tvProductSellPrice = itemView.findViewById(R.id.tvProductSellPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), R.id.context_product_Sell, 0, R.string.lbContextProductSell);
            menu.add(this.getAdapterPosition(), R.id.context_product_Update, 1, R.string.lbContextUpdate);
            menu.add(this.getAdapterPosition(), R.id.context_product_delete, 2, R.string.lbContextDel);
        }


    }
}
