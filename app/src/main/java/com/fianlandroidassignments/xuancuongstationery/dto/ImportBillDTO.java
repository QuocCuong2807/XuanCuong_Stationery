package com.fianlandroidassignments.xuancuongstationery.dto;

public class ImportBillDTO {
    private int billId;
    private String date;
    private int totalPrice;

    //with id
    public ImportBillDTO(int billId, String date, int totalPrice) {
        this.billId = billId;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    //without id
    public ImportBillDTO(String date, int totalPrice) {
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
