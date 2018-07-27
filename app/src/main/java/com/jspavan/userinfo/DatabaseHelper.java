package com.jspavan.userinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


/**
 * Created by pavan on 7/26/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME ="info.db";
    public static final String TABLE_NAME ="info_table";
    public static final String Col1 ="ID";
    public static final String Col2 ="NAME";
    public static final String Col3 ="EMAIL";
    public static final String Col4 ="PHONE";
    public static final String Col5 ="MANU";
    public static final String Col6 ="MODEL";
    public static final String Col7 ="REG";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " +TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, EMAIL TEXT, PHONE TEXT UNIQUE, MANU TEXT, MODEL TEXT, REG TEXT)";

        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,  int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP  TABLE IF EXISTS " +TABLE_NAME );
        onCreate(sqLiteDatabase);

    }

    public boolean addData(String name, String email , String phone , String manu,String model,String reg) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor resultset = sqLiteDatabase.rawQuery("SELECT PHONE FROM info_table WHERE PHONE='" + phone + "'", null);

        if (resultset.getCount()==0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col2, name);
            contentValues.put(Col3, email);
            contentValues.put(Col4, phone);
            contentValues.put(Col5, manu);
            contentValues.put(Col6, model);
            contentValues.put(Col7, reg);

             sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
           return true;
        } else {
                 return false;

        }



    }
    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

}
