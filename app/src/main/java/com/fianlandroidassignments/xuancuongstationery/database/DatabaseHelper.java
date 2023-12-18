package com.fianlandroidassignments.xuancuongstationery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fianlandroidassignments.xuancuongstationery.dto.CategoryDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.ProviderDTO;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "XuanCuongStationery.db";
    private static final int DATABASE_VERSION = 1;
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
}
