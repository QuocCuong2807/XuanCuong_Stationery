package com.fianlandroidassignments.xuancuongstationery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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

    public long insertNewProvider(ProviderDTO providerDTO){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ProviderTable.PROVIDER_NAME, providerDTO.getName());
        contentValues.put(ProviderTable.PROVIDER_IMAGE, providerDTO.getImage());

        return sqLiteDatabase.insert(ProviderTable.TABLE_NAME, null, contentValues);
    }

    public List<ProviderDTO> selectAllCategory(){
        List<ProviderDTO> providerList = new ArrayList<>();
        String sql = "SELECT * FROM " + ProviderTable.TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

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

    public long deleteProvider(int provider_id){
        sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(ProviderTable.TABLE_NAME,
                ProviderTable.PROVIDER_ID + " = ? ", new String[]{String.valueOf(provider_id)});
        return result;
    }
}
