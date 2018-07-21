package com.outnative.bluebus.SqlLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by milan on 9/15/2017.
 */
public class PassengerDetailsSql extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="Bluebus.db";
    public final static String TABLE_NAME="passenger_details";

    public PassengerDetailsSql(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + " (seat_number text,passenger_name text,passenger_gender text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void delete(SQLiteDatabase db){
        db.delete(TABLE_NAME,null,null);
    }

    public void deleteRows(SQLiteDatabase db){
        db.execSQL("delete from "+TABLE_NAME);
    }
}
