package com.fianlandroidassignments.xuancuongstationery.dto;

import android.content.Context;

import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;

import java.util.HashMap;
import java.util.Map;

public class SellWaitingList {
    private static SellWaitingList instance;

    private Map<Integer, SoldBillDetailDTO> sellWaitingList;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SellWaitingList(Context context) {
        this.sellWaitingList = new HashMap<>();
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public static SellWaitingList getInstance(Context context){
        if (instance == null)
            instance = new SellWaitingList(context);
        return instance;
    }

    public boolean isEmpty(){
        if (sellWaitingList.isEmpty())
            return true;
        return false;
    }

    public void addProductToSellList(int productId, int quantity){
        if (sellWaitingList.containsKey(Integer.valueOf(productId)))
            sellWaitingList.get(Integer.valueOf(productId)).plusProductQuantity(quantity);
        else {
            ProductDTO newProduct = databaseHelper.selectProductById(productId);
            SoldBillDetailDTO soldBillDetail = new SoldBillDetailDTO();

            soldBillDetail.setProduct(newProduct);
            soldBillDetail.setProductPrice(newProduct.getSell_price());
            soldBillDetail.setProductQuantity(quantity);
            soldBillDetail.setSoldPrice(newProduct.getSell_price() * newProduct.getProduct_quantity());

            //put key and value to HashMap
            sellWaitingList.put(Integer.valueOf(productId), soldBillDetail);
        }
    }

    public void deleteProductFromSellList(int productId){
        if (sellWaitingList.containsKey(Integer.valueOf(productId)))
            sellWaitingList.remove(Integer.valueOf(productId));
    }

    public void minusQuantityFromSellList(int productId){
        if (sellWaitingList.get(Integer.valueOf(productId)).getProductQuantity() > 1)
            sellWaitingList.get(Integer.valueOf(productId)).minusProductQuantity();
        else
            deleteProductFromSellList(productId);
    }

    public int getTotalPriceInSellList(){
        int sum = 0;
        for (Map.Entry<Integer, SoldBillDetailDTO> entry: sellWaitingList.entrySet()
             ) {
            sum+= (entry.getValue().getProductPrice() * entry.getValue().getProductQuantity());
        }
        return sum;
    }

    //getter setter

    public Map<Integer, SoldBillDetailDTO> getSellWaitingList() {
        return sellWaitingList;
    }

    public void setSellWaitingList(Map<Integer, SoldBillDetailDTO> sellWaitingList) {
        this.sellWaitingList = sellWaitingList;
    }
}
