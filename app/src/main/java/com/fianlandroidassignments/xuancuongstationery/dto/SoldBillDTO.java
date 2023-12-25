package com.fianlandroidassignments.xuancuongstationery.dto;

public class SoldBillDTO {
    private int billId;
    private String date;
    private int totalPrice;

    public SoldBillDTO(int billId, String date, int totalPrice) {
        this.billId = billId;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public SoldBillDTO(String date, int totalPrice) {
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
