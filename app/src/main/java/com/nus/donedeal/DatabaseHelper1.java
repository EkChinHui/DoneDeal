package com.nus.donedeal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper1 extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Table_2";
    public static final String COL0 = "ID";
    private static final String COL1 = "Description";
    private static final String COL2 = "Price";
    private static final String COL3 = "Paid_by";

    public DatabaseHelper1(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TEXT," + COL2 + " REAL, " + COL3 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i , int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String description, Float price, String paidBy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(3);
        contentValues.put(COL1, description);
        contentValues.put(COL2, price);
        contentValues.put(COL3, paidBy);

        long result = db.insert(TABLE_NAME, null, contentValues);

        // if date is inserted incorrectly it will return -1
        if (result == -1) return false;
        else return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //returns only the ID that matches the name
    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public String[] getExpenseRow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String[] stringArr = new String[3];
        if (c != null) {
            while (c.getInt(c.getColumnIndex(COL0)) != (id)) {
                c.moveToNext();
            }
            String Description = c.getString(c.getColumnIndex(COL1));
            Float Amount = c.getFloat(c.getColumnIndex(COL2));
            String PaidBy = c.getString(c.getColumnIndex(COL3));

            stringArr[0] = Description;
            stringArr[1] = Amount.toString();
            stringArr[2] = PaidBy;
        }
        return stringArr;
    }

    public void deleteEntry(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + " = '" + id + "' AND " + COL1 +
                " = '" + name + "'";
        db.execSQL(query);
    }

}
