package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HabitDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =2;
    private static final String DATABASE_NAME = "habitsdb";
    private static final String DATABASE_TABLE = "habitstable";

    //Columns for database table
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_COUNT = "count";
    private static final String KEY_COUNTNAME = "countName";
    private static final String KEY_SESSION = "sessionTracking";
    private static final String KEY_TIME = "time";



    HabitDatabase(Context context){

        super(context, DATABASE_NAME,null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table
        //CREATE TABLE habitDatabase(id INT PRIMARY KEY, name TEXT, count INT, countName TEXT, sessionTracking INT, time TEXT)
        String query = "CREATE TABLE " + DATABASE_TABLE + "("+KEY_ID+" INT PRIMARY KEY,"+
                KEY_NAME +" TEXT," +
                KEY_COUNT + " TEXT," +
                KEY_COUNTNAME + " TEXT," +
                KEY_SESSION + " TEXT," +
                KEY_TIME + " TEXT" +")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion >= newVersion){
            return;
        }else {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public long addHabit(Habits habits){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_NAME, habits.getName());
        c.put(KEY_COUNT, habits.getCount());
        c.put(KEY_COUNTNAME, habits.getCountName());
        c.put(KEY_SESSION, habits.getSessionTracking());
        c.put(KEY_TIME, habits.getTime());

        long ID = db.insert(DATABASE_TABLE, null, c);
        //Log.d("Inserted", "ID ---> " + ID);
        return ID;


    }
}
