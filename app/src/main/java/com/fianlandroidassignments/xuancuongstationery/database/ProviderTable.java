package com.fianlandroidassignments.xuancuongstationery.database;

public class ProviderTable {
    public static final String TABLE_NAME = "Provider";
    public static final String PROVIDER_ID = "provider_id";
    public static final String PROVIDER_NAME = "provider_name";
    public static final String PROVIDER_IMAGE = "image";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    PROVIDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PROVIDER_NAME + " TEXT, " +
                    PROVIDER_IMAGE + " BLOB)";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
    public static final String SELECT_PROVIDER_BY_ID = " SELECT * FROM " + TABLE_NAME + " WHERE " + PROVIDER_ID + " = ? ";
}
