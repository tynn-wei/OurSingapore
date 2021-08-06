package com.example.oursingaporetynnwei;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ourSingapore.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ISLANDS = "Island";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_SQKM = "sqkm";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Song
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,
        // singers TEXT, stars INTEGER, year INTEGER );
        String createIslandTableSql = "CREATE TABLE " + TABLE_ISLANDS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESC + " TEXT, "
                + COLUMN_SQKM + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createIslandTableSql);
        Log.i("info", createIslandTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLANDS);
        onCreate(db);
    }

    public long insertIsland(String title, String desc, int sqkm, int stars) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, title);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_SQKM, sqkm);
        values.put(COLUMN_STARS, stars);
        // Insert the row into the TABLE_SONG
        long result = db.insert(TABLE_ISLANDS, null, values);
        // Close the database connection
        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Island> getAllIsland() {
        ArrayList<Island> islandslist = new ArrayList<Island>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_DESC + ","
                + COLUMN_SQKM + ","
                + COLUMN_STARS + " FROM " + TABLE_ISLANDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int sqkm = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Island newIsland = new Island(id, name, desc, sqkm, stars);
                islandslist.add(newIsland);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return islandslist;
    }

    public ArrayList<Island> getAllIslandsByStars(int starsFilter) {
        ArrayList<Island> islandslist = new ArrayList<Island>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESC, COLUMN_SQKM, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        //String selectQuery = "SELECT " + COLUMN_ID + ","
        //            + COLUMN_TITLE + ","
        //            + COLUMN_SINGERS + ","
        //            + COLUMN_YEAR + ","
        //            + COLUMN_STARS
        //            + " FROM " + TABLE_SONG;

        Cursor cursor;
        cursor = db.query(TABLE_ISLANDS, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int sqKm = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Island newIsland = new Island(id, name, desc, sqKm, stars);
                islandslist.add(newIsland);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return islandslist;
    }

    public int updateIsland(Island data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESC, data.getDesc());
        values.put(COLUMN_SQKM, data.getSqkm());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ISLANDS, values, condition, args);
        db.close();
        return result;
    }


    public int deleteIsland(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLANDS, condition, args);
        db.close();
        return result;
    }

}

