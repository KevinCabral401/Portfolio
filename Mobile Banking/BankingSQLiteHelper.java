package com.example.mobilebanking;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class BankingSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "accounts.sqlite";
    private static final int DB_VERSION = 1;
    Context context;


    public BankingSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE accounts (_id INTEGER PRIMARY KEY AUTOINCREMENT, number STRING UNIQUE, name STRING, amount DOUBLE, type STRING, user_id INTEGER, FOREIGN KEY(user_id) REFERENCES users(username));";
        sqLiteDatabase.execSQL(create);
    }

    void insert(String number, String name, double amount, String type){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("name", name);
        contentValues.put("amount", amount);
        contentValues.put("type", type);
        sqLiteDatabase.insert("accounts", null, contentValues);
    }

    void delete(String number){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete("accounts", "number = ?", new String[]{String.valueOf(number)});
    }

    public void updateAmount(int position, double amount){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        sqLiteDatabase.update("accounts", contentValues, "_id = ?", new String[]{String.valueOf(position+1)});
    }

    public ArrayList<String> getAccounts(){
        ArrayList<String> array = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("accounts", new String[]{"number", "name", "amount", "type"}, null, null, null, null, null);
        for(int i = 0; i < cursor.getCount(); i++){
            if(cursor.moveToPosition(i)){
                Account myAccount = new Account(cursor.getString(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
                Log.v("Here", String.valueOf(myAccount));
                array.add(myAccount.getNumber());
            }
        }
        Log.v("Here", String.valueOf(array));
        return array;
    }

    int getCount(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM accounts", null);
        return cursor.getCount();
    }

    public Account getAccount(int position) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("accounts", new String[]{"number", "name", "amount", "type"},
                null, null, null, null, null);
        if(cursor.moveToPosition(position)){
            Account myAccount = new Account(cursor.getString(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
            return myAccount;
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

