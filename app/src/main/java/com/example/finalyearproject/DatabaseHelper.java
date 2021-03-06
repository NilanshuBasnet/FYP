package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "habits_table";

    //Columns for database table
    private static final String KEY_NAME = "name";
    private static final String KEY_COUNT = "count";
    private static final String KEY_COUNTNAME = "countName";
    private static final String KEY_SESSION = "sessionTracking";
    private static final String KEY_TIME = "time";


    public DatabaseHelper(Context context){
        super(context,TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME + "("+ KEY_NAME +" TEXT PRIMARY KEY," +
                KEY_COUNT + " TEXT," +
                KEY_COUNTNAME + " TEXT," +
                KEY_SESSION + " TEXT," +
                KEY_TIME + " TEXT" +")";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion >= newVersion){
            return;
        }else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }

    public boolean insertHabitData (String KEY_NAME,  String KEY_COUNT, String KEY_COUNTNAME, String KEY_SESSION, String KEY_TIME){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("name", KEY_NAME);
        c.put("count", KEY_COUNT);
        c.put("countName", KEY_COUNTNAME);
        c.put("sessionTracking", KEY_SESSION);
        c.put("time", KEY_TIME);

        long result = db.insert(TABLE_NAME,null,c);

        //if data is not inserted correctly it will return -1
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }


    public boolean deleteHabitData (String KEY_NAME){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE name = ?", new String[]{KEY_NAME});
        if (cursor.getCount() > 0) {
            long result = db.delete(TABLE_NAME, "name=?", new String[]{KEY_NAME});

            //if data is not inserted correctly it will return -1
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }



    public Cursor getHabitData (){

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }



}