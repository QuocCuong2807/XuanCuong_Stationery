package com.fianlandroidassignments.xuancuongstationery.database;

public class ImportBillTable {
    public static final String TABLE_NAME = "ImportBill";
    public static final String IMPORT_BILL_ID = "importBill_id";
    public static final String IMPORT_BILL_DATE = "payment_date";
    public static final String IMPORT_BILL_TOTAL_PRICE = "total_price";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    IMPORT_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMPORT_BILL_DATE + " TEXT, " +
                    IMPORT_BILL_TOTAL_PRICE + " INTEGER)";
    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static final String SELECT_IMPORT_BILL_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + IMPORT_BILL_ID + " = ? ";
    public static final String SELECT_ALL_IMPORT_BILL = "SELECT * FROM " + TABLE_NAME;
}
