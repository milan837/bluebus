package com.outnative.bluebus.SqlLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by milan on 8/22/2017.
 */
public class LocationSql extends SQLiteOpenHelper {
    public final static String DATABASE_NAME="bluebus.db";
    public final static String TABLE_NAME="locations";

    public LocationSql(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + "(location_id integer,location_name text,location_station)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void delete(SQLiteDatabase db){
        db.delete(TABLE_NAME,null,null);
    }
}
