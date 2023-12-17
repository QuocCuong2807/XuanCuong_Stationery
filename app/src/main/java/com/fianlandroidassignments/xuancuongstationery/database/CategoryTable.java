package com.fianlandroidassignments.xuancuongstationery.database;

public class CategoryTable {
    public static final String TABLE_NAME = "Category";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_IMAGE = "image";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_NAME + " TEXT, " +
                    CATEGORY_IMAGE + " BLOB)";
}
