package com.fianlandroidassignments.xuancuongstationery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDetailDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProductDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "XuanCuongStationery.db";
    private static final int DATABASE_VERSION = 5;
    SQLiteDatabase sqLiteDatabase;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table
        db.execSQL(CategoryTable.CREATE_TABLE_QUERY);
        db.execSQL(ProviderTable.CREATE_TABLE_QUERY);
        db.execSQL(ProductTable.CREATE_TABLE_QUERY);
        db.execSQL(ImportBillTable.CREATE_TABLE_QUERY);
        db.execSQL(ImportBillDetailTable.CREATE_TABLE_QUERY);
        db.execSQL(SoldBillTable.CREATE_TABLE_QUERY);
        db.execSQL(SoldBillDetailTable.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /* to update db pls update version  */

        /*db.execSQL(SoldBillDetailTable.DROP_TABLE_QUERY);
        db.execSQL(SoldBillTable.DROP_TABLE_QUERY);
        db.execSQL(ImportBillDetailTable.DROP_TABLE_QUERY);
        db.execSQL(ImportBillTable.DROP_TABLE_QUERY);
        db.execSQL(ProductTable.DROP_TABLE_QUERY);
        db.execSQL(ProviderTable.DROP_TABLE_QUERY);
        db.execSQL(CategoryTable.DROP_TABLE_QUERY);
        onCreate(db);*/

    }




                                    /* MANIPULATE WITH PROVIDER TABLE*/

    //insert new record in provider table
    public long insertNewProvider(ProviderDTO providerDTO){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ProviderTable.PROVIDER_NAME, providerDTO.getName());
        contentValues.put(ProviderTable.PROVIDER_IMAGE, providerDTO.getImage());

        return sqLiteDatabase.insert(ProviderTable.TABLE_NAME, null, contentValues);
    }

    //get all record in provider table
    public List<ProviderDTO> selectAllProvider(){
        List<ProviderDTO> providerList = new ArrayList<>();

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(ProviderTable.SELECT_ALL_QUERY, null);

        if (cursor.moveToFirst()){
            do {
                int provider_id = cursor.getInt(0);
                String provider_name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);

                providerList.add(new ProviderDTO(provider_id,provider_name,image));
            }while (cursor.moveToNext());
        }
        cursor.close();

        return providerList;
    }

    public ProviderDTO selectProviderById(int id){
        sqLiteDatabase = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(id)};
        ProviderDTO providerDTO;

        Cursor cursor = sqLiteDatabase.rawQuery(CategoryTable.SELECT_CATEGORY_BY_ID,selectionArgs);

        if (cursor.moveToFirst()){
            int provider_id = cursor.getInt(0);
            String provider_name = cursor.getString(1);
            byte[] provider_img = cursor.getBlob(2);

            cursor.close();

            return new ProviderDTO(provider_id,provider_name,provider_img);
        }
        return null;
    }

    //delete 1 record in provider table by id
    public long deleteProvider(int provider_id){
        sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(ProviderTable.TABLE_NAME,
                ProviderTable.PROVIDER_ID + " = ? ", new String[]{String.valueOf(provider_id)});
        return result;
    }

    //update 1 record in provider table
    public int updateProvider(int oldId, ProviderDTO newValues){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ProviderTable.PROVIDER_NAME, newValues.getName());
        contentValues.put(ProviderTable.PROVIDER_IMAGE, newValues.getImage());

        int numOfRowAffected = sqLiteDatabase.update(ProviderTable.TABLE_NAME, contentValues,
                    ProviderTable.PROVIDER_ID+ " = ? ", new String[]{String.valueOf(oldId)});

        return numOfRowAffected;
    }

                                    /* MANIPULATE WITH CATEGORY TABLE*/

    //insert new record in category table
    public long insertNewCategory(CategoryDTO categoryDTO){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CategoryTable.CATEGORY_NAME, categoryDTO.getCategory_name());
        contentValues.put(CategoryTable.CATEGORY_IMAGE, categoryDTO.getImage());

        return sqLiteDatabase.insert(CategoryTable.TABLE_NAME, null, contentValues);
    }

    //get all record in category table include product quantity
    public List<CategoryDTO> selectAllCategory(){
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(CategoryTable.SELECT_ALL_WITH_QUANTITY,null);

        if (cursor.moveToFirst()){
            do {

                int category_id = cursor.getInt(0);
                String category_name = cursor.getString(1);
                byte [] category_image = cursor.getBlob(2);
                int product_quantity = cursor.getInt(3);

                categoryDTOList.add(new CategoryDTO(category_id, category_name, category_image, product_quantity));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return categoryDTOList;
    }

    //delete 1 record in category table by id
    public long deleteCategory(int category_id){
        sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(CategoryTable.TABLE_NAME,
                CategoryTable.CATEGORY_ID + " = ? ", new String[]{String.valueOf(category_id)});
        return result;
    }

    //update 1 record in provider table
    public int updateCategory(int oldId, CategoryDTO newValues){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CategoryTable.CATEGORY_NAME, newValues.getCategory_name());
        contentValues.put(CategoryTable.CATEGORY_IMAGE, newValues.getImage());

        int numOfRowAffected = sqLiteDatabase.update(CategoryTable.TABLE_NAME, contentValues,
                CategoryTable.CATEGORY_ID+ " = ? ", new String[]{String.valueOf(oldId)});

        return numOfRowAffected;
    }

    public CategoryDTO selectCategoryById(int id){
        sqLiteDatabase = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = sqLiteDatabase.rawQuery(CategoryTable.SELECT_CATEGORY_BY_ID,selectionArgs);

        if (cursor.moveToFirst()){
            int cate_id = cursor.getInt(0);
            String cate_name = cursor.getString(1);
            byte[] cate_img = cursor.getBlob(2);

            cursor.close();

            return new CategoryDTO(cate_id, cate_name, cate_img);
        }
        return null;
    }

                                    /* MANIPULATE WITH PRODUCT TABLE*/

    public long insertNewProduct(ProductDTO productDTO){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ProductTable.PRODUCT_NAME, productDTO.getProduct_name());
        contentValues.put(ProductTable.PRODUCT_IMAGE, productDTO.getImage());
        contentValues.put(ProductTable.PRODUCT_IMPORT_PRICE, productDTO.getImport_price());
        contentValues.put(ProductTable.PRODUCT_SOLD_PRICE, productDTO.getSell_price());
        contentValues.put(ProductTable.PRODUCT_QUANTITY, productDTO.getProduct_quantity());
        contentValues.put(ProductTable.PRODUCT_STATUS, productDTO.getProduct_status());
        contentValues.put(ProductTable.PRODUCT_DESCRIPTION, productDTO.getProduct_description());
        contentValues.put(ProductTable.CATEGORY_ID, productDTO.getCategory().getCategory_id());
        contentValues.put(ProductTable.PROVIDER_ID, productDTO.getProvider().getId());

        long newProductId = sqLiteDatabase.insert(ProductTable.TABLE_NAME, null, contentValues);

        return newProductId;
    }

    public ProductDTO selectProductById(int id)
    {
        sqLiteDatabase = this.getReadableDatabase();

        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = sqLiteDatabase.rawQuery(ProductTable.SELECT_PRODUCT_BY_ID,selectionArgs);
        ProductDTO productDTO;

        if (cursor.moveToFirst()){
            int product_id = cursor.getInt(0);
            String product_name = cursor.getString(1);
            byte [] product_image = cursor.getBlob(2);
            int product_quantity = cursor.getInt(3);
            int product_status = cursor.getInt(4);
            String product_desc = cursor.getString(5);
            int import_price = cursor.getInt(6);
            int sell_price = cursor.getInt(7);
            int cate_id = cursor.getInt(8);
            int provider_id = cursor.getInt(9);

            ProviderDTO providerDTO = selectProviderById(provider_id);
            CategoryDTO categoryDTO = selectCategoryById(cate_id);

            productDTO = new ProductDTO(product_id,product_name,product_image,product_quantity,import_price,
                                    sell_price,product_status,product_desc,categoryDTO, providerDTO);
            cursor.close();

            return productDTO;

        }
        return null;
    }

    public List<ProductDTO> selectProductByCateId(int categoryId)
    {
        sqLiteDatabase = this.getReadableDatabase();

        List<ProductDTO> productDTOList = new ArrayList<>();

        String[] selectionArgs = {String.valueOf(categoryId)};

        Cursor cursor = sqLiteDatabase.rawQuery(ProductTable.SELECT_PRODUCT_BY_CATE_ID,selectionArgs);

        if (cursor.moveToFirst()){
            do {
                int product_id = cursor.getInt(0);
                String product_name = cursor.getString(1);
                byte [] product_image = cursor.getBlob(2);
                int product_quantity = cursor.getInt(3);
                int product_status = cursor.getInt(4);
                String product_desc = cursor.getString(5);
                int import_price = cursor.getInt(6);
                int sell_price = cursor.getInt(7);
                int cate_id = cursor.getInt(8);
                int provider_id = cursor.getInt(9);

                ProviderDTO providerDTO = selectProviderById(provider_id);
                CategoryDTO categoryDTO = selectCategoryById(cate_id);


                productDTOList.add(new ProductDTO(product_id,product_name,product_image,product_quantity,import_price,
                        sell_price,product_status,product_desc,categoryDTO, providerDTO));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return productDTOList;
    }


    //update product quantity

    public int updateProductQuantity(int existingProductId,int newQuantity){

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //get product need to update by id
        ProductDTO existingProduct = selectProductById(existingProductId);

        //update quantity
        int updatedQuantity = existingProduct.getProduct_quantity() + newQuantity;

        contentValues.put(ProductTable.PRODUCT_QUANTITY, updatedQuantity);
        String[] selectionArgs = {String.valueOf(existingProductId)};


        int numOfRowAffected = sqLiteDatabase.update(ProductTable.TABLE_NAME, contentValues,
                ProductTable.PRODUCT_ID + " = ? ", selectionArgs);

        return numOfRowAffected;
    }

                                /* MANIPULATE WITH IMPORT BILL TABLE*/
    public long insertNewImportBill(ImportBillDTO importBillDTO){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ImportBillTable.IMPORT_BILL_DATE, importBillDTO.getDate());
        contentValues.put(ImportBillTable.IMPORT_BILL_TOTAL_PRICE, importBillDTO.getTotalPrice());

        long newBillId = sqLiteDatabase.insert(ImportBillTable.TABLE_NAME,null ,contentValues);

        return newBillId;
    }

    public int updateImportTotalPrice(int id,int totalPrice)
    {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ImportBillTable.IMPORT_BILL_TOTAL_PRICE, totalPrice);

        int numOfRowAffected = sqLiteDatabase.update(ImportBillTable.TABLE_NAME, contentValues,
                ImportBillTable.IMPORT_BILL_ID+ " = ? ", new String[]{String.valueOf(id)});

        return numOfRowAffected;
    }

    public ImportBillDTO selectImportById(int id){
        sqLiteDatabase = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(id)};
        ImportBillDTO importBillDTO;

        Cursor cursor = sqLiteDatabase.rawQuery(ImportBillTable.SELECT_IMPORT_BILL_BY_ID,selectionArgs);

        if (cursor.moveToFirst()){
            int import_id = cursor.getInt(0);
            String import_date = cursor.getString(1);
            int import_totalPrice = cursor.getInt(2);

            cursor.close();

            return new ImportBillDTO(import_id, import_date, import_totalPrice);
        }
        return null;
    }

                                /* MANIPULATE WITH IMPORT BILL DETAIL TABLE*/

    public long insertNewImportBillDetail(ProductDTO productDTO, ImportBillDTO importBillDTO){

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ImportBillDetailTable.IMPORT_BILL_ID, importBillDTO.getBillId());
        contentValues.put(ImportBillDetailTable.PRODUCT_ID, productDTO.getProduct_id());
        contentValues.put(ImportBillDetailTable.PRODUCT_QUANTITY, productDTO.getProduct_quantity());
        contentValues.put(ImportBillDetailTable.PRODUCT_PRICE, productDTO.getImport_price());
        contentValues.put(ImportBillDetailTable.BILL_DETAIL_PRICE, productDTO.getImport_price() * productDTO.getProduct_quantity());

        long newImportBillDetailId = sqLiteDatabase.insert(ImportBillDetailTable.TABLE_NAME, null, contentValues);

        return newImportBillDetailId;
    }

    public List<ImportBillDetailDTO> selectAllBillDetailByBillId(int billId){
        List<ImportBillDetailDTO> importBillDetailDTOList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(billId)};


        Cursor cursor = sqLiteDatabase.rawQuery(ImportBillDetailTable.SELECT_ALL_IMPORT_DETAIL_BY_BILL_ID,selectionArgs);

        if (cursor.moveToFirst()){
            do {
                int productQuantity = cursor.getInt(1);
                int productPrice = cursor.getInt(2);
                int importPrice = cursor.getInt(3);

                importBillDetailDTOList.add(new ImportBillDetailDTO(productQuantity,productPrice,importPrice));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return importBillDetailDTOList;
    }

}
