package com.fianlandroidassignments.xuancuongstationery.database;

public class ProductTable {
    public static final String TABLE_NAME = "Product";
    public static final String CATEGORY_TABLE_NAME = CategoryTable.TABLE_NAME;
    public static final String PROVIDER_TABLE_NAME = ProviderTable.TABLE_NAME;
    public static final String CATEGORY_ID = CategoryTable.CATEGORY_ID;
    public static final String PROVIDER_ID = ProviderTable.PROVIDER_ID;
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_IMAGE = "image";
    public static final String PRODUCT_QUANTITY = "product_quantity";
    public static final String PRODUCT_IMPORT_PRICE = "product_import_price";
    public static final String PRODUCT_SOLD_PRICE = "product_sold_price";
    public static final String PRODUCT_STATUS = "product_status";
    public static final String PRODUCT_DESCRIPTION = "product_description";


    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCT_NAME + " TEXT, " +
                    PRODUCT_IMAGE + " BLOB, " +
                    PRODUCT_QUANTITY + " INTEGER, " +
                    PRODUCT_STATUS + " INTEGER, " +
                    PRODUCT_DESCRIPTION + " TEXT, " +
                    PRODUCT_IMPORT_PRICE + " INTEGER, " +
                    PRODUCT_SOLD_PRICE + " INTEGER, " +
                    CATEGORY_ID + " INTEGER, " +
                    PROVIDER_ID + " INTEGER, " +
                    " FOREIGN KEY " + "("+CATEGORY_ID+") REFERENCES "+ CATEGORY_TABLE_NAME + "("+CATEGORY_ID+")" +
                    " FOREIGN KEY " + "("+PROVIDER_ID+") REFERENCES "+ PROVIDER_TABLE_NAME + "("+PROVIDER_ID+")" +")";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String SELECT_ALL_PRODUCTS = "SELECT * FROM " + TABLE_NAME;

    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRODUCT_ID + " = ?";

    public static final String SELECT_PRODUCT_BY_CATE_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + CATEGORY_ID + " = ?";
}
