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
}
