package com.fianlandroidassignments.xuancuongstationery.database;

public class ImportBillDetailTable {
    public static final String TABLE_NAME = "ImportBillDetail";
    public static final String IMPORT_BILL_TABLE_NAME = ImportBillTable.TABLE_NAME;
    public static final String PRODUCT_TABLE_NAME = ProductTable.TABLE_NAME;
    public static final String IMPORT_BILL_ID = ImportBillTable.IMPORT_BILL_ID;
    public static final String PRODUCT_ID = ProductTable.PRODUCT_ID;
    public static final String IMPORT_BILL_DETAIL_ID = "id";
    public static final String PRODUCT_QUANTITY = "product_quantity";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String BILL_DETAIL_PRICE = "billDetail_price";
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    IMPORT_BILL_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCT_QUANTITY + " INTEGER, " +
                    PRODUCT_PRICE + " INTEGER,  " +
                    BILL_DETAIL_PRICE + " INTEGER, " +
                    IMPORT_BILL_ID + " INTEGER, " +
                    PRODUCT_ID + " INTEGER, " +
                    " FOREIGN KEY " + "("+IMPORT_BILL_ID+") REFERENCES "+ IMPORT_BILL_TABLE_NAME + "("+IMPORT_BILL_ID+")" +
                    " FOREIGN KEY " + "("+PRODUCT_ID+") REFERENCES "+ PRODUCT_TABLE_NAME + "("+PRODUCT_ID+")" +")";

    public static final String SELECT_ALL_IMPORT_DETAIL_BY_BILL_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + IMPORT_BILL_ID + " = ? ";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
