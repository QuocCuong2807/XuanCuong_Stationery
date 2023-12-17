package com.fianlandroidassignments.xuancuongstationery.database;

public class SoldBillTable {
    public static final String TABLE_NAME = "SoldBill";
    public static final String SOLD_BILL_ID = "soldBill_id";
    public static final String SOLD_BILL_DATE = "payment_date";
    public static final String SOLD_BILL_TOTAL_PRICE = "total_price";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    SOLD_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SOLD_BILL_DATE + " TEXT, " +
                    SOLD_BILL_TOTAL_PRICE + " INTEGER)";
}
