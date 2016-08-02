package com.example.personalaccount.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personalaccount.utils.Password;


/**
 * Created by Administrator on 2015/2/9.
 */
public class PasswordDAO {
    private MyDatebaseHelper helper;
    private SQLiteDatabase db;

    public PasswordDAO(Context context) {
        helper = new MyDatebaseHelper(context);
    }

    /**
     * 添加密码信息
     */
    public void add(Password password) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into pwd (password) values (?)",
                new Object[] {password.getPassword()});
    }

    /**
     * 更改密码
     */
    public void update(Password password) {
        db = helper.getWritableDatabase();
        db.execSQL("update pwd set password = ?",
                new Object[] {password.getPassword()});
    }

    /**
     * 查找密码
     */
    public Password find() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select password from pwd", null);
        if (cursor.moveToNext()) {
            return new Password(cursor.getString(cursor.getColumnIndex("password")));
        }
        return null;
    }

    public void delete(String pwd) {


            db = helper.getWritableDatabase();
            db.execSQL("delete from pwd where password is ?", new Object[] {pwd}) ;

    }

    public long getCount() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(password) from pwd", null);
        if (cursor.moveToNext()) {
            return cursor.getLong(0);
        }
        return 0;
    }
}
