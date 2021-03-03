package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public Habits getHabit(long id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,KEY_NAME,KEY_COUNT,KEY_COUNTNAME,KEY_SESSION,KEY_TIME}, KEY_ID+"=?",
                new String[]{String.valueOf(id)}, null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        return new Habits(cursor.getLong(0),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5));
    }

    public List<Habits> getHabits(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Habits> allHabits = new ArrayList<>();
        //Select * from database
        String query = "SELECT * FROM "+ DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Habits habits = new Habits();
                habits.setID(cursor.getLong(0));
                habits.setName(cursor.getString(1));
                habits.setCount(cursor.getString(2));
                habits.setCountName(cursor.getString(3));
                habits.setSessionTracking(cursor.getString(4));
                habits.setTime(cursor.getString(5));

                allHabits.add(habits);

            }while(cursor.moveToNext());
        }
        return allHabits;
    }
}

