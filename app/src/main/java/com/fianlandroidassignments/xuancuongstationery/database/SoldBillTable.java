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

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String SELECT_ALL_SOLD_BILL = "SELECT * FROM " + TABLE_NAME;

    public static final String SELECT_SOLD_BILL_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE "+ SOLD_BILL_ID  + " = ? ";

    public static final String SELECT_TOTAL_REVENUE = "SELECT  sum(s.total_price) as Total_revenue\n" +
            "FROM SoldBill s\n";
}
