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

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL_WITH_QUANTITY =
            "SELECT  c." + CATEGORY_ID + ", c." + CATEGORY_NAME
            + ", c." + CATEGORY_IMAGE
            +", IFNULL( SUM(p."+ProductTable.PRODUCT_QUANTITY+"), 0)"
            + " FROM " + TABLE_NAME + " c "
            + " LEFT JOIN " + ProductTable.TABLE_NAME + " p "
            + " ON c." + CATEGORY_ID + " = " + "p." + ProductTable.CATEGORY_ID
            +" GROUP BY c."+ CATEGORY_ID;
}
