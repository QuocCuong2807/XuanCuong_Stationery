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
            +", ifnull( SUM(p."+ProductTable.PRODUCT_QUANTITY+"), 0) as sl"
            + " FROM " + TABLE_NAME + " c "
            + " LEFT JOIN " + ProductTable.TABLE_NAME + " p "
            + " ON c." + CATEGORY_ID + " = " + "p." + ProductTable.CATEGORY_ID
            +" GROUP BY c."+ CATEGORY_ID;

    public static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM "+ TABLE_NAME + " WHERE " + CATEGORY_ID + " = ? ";

    public static final String SELECT_TOTAL_REVENUE = "SELECT c.category_id, c.category_name, sum(s.billDetail_price) as total_revenue\n" +
            "FROM Category c JOIN Product p on c.category_id = p.category_id\n" +
            "\t\t\t\tJOIN SoldBillDetail s on p.product_id = s.product_id\n" +
            "GROUP BY c.category_id, c.category_name";
}
