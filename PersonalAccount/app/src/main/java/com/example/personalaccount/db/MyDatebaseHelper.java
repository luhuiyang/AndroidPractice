package com.example.personalaccount.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/2/6.
 */
public class MyDatebaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DBNAME = "myaccount.db";

    public MyDatebaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDatebaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table getaccount(" +
                "id integer primary key autoincrement," +
                "money decimal," +
                "time timestamp," +
                "type varchar(10)," +
                "mark varchar(200)," +
                "location varchar(100))");
        db.execSQL("create table costaccount(" +
                "id integer primary key autoincrement," +
                "money decimal," +
                "time timestamp," +
                "type varchar(10)," +
                "mark varchar(200)," +
                "location varchar(100))");
        db.execSQL("create table pwd (" +
                "password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
