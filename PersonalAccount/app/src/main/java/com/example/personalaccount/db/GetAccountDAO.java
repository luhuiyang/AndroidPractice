package com.example.personalaccount.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personalaccount.entity.GetAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/2/9.
 */
public class GetAccountDAO { //进行收入信息管理，包括添加，修改，删除，查询，以及获取总记录数等功能
    private MyDatebaseHelper helper;
    private SQLiteDatabase db;
    public GetAccountDAO(Context context) {
        helper = new MyDatebaseHelper(context);
    }

    /**
     * 添加收入信息
     */
    public void add(GetAccount getAccount) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into getaccount (" +
                "id, money, time, type, mark, location) values (?, ? ,? ,? ,?, ?)",
                new Object[] {getAccount.getId(), getAccount.getMoney(), getAccount.getTime(),
                        getAccount.getType(), getAccount.getMark(), getAccount.getLocation()});
    }

    public void update(GetAccount getAccount) {
        db = helper.getWritableDatabase();
        db.execSQL("update getaccount set money = ?, time = ?, type = ?, mark = ?, location = ? where id = ?",
                new Object[] {getAccount.getMoney(), getAccount.getTime(), getAccount.getType(),
                getAccount.getMark(), getAccount.getLocation(), getAccount.getId()});
    }

    public GetAccount find(int id) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id, money, time, type, mark,location from getaccount where id= ?",
                new String[] {String.valueOf(id)});
        if (cursor.moveToNext()) { // 讲收入信息遍历到getaccount类里
            return new GetAccount(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("mark")),
                    cursor.getString(cursor.getColumnIndex("location")));
        }
        return null;
    }

    public GetAccount find(String starttime, String finishtime) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id, money, time, type, mark,location from getaccount where time between ? and ?",
                new String[] {starttime, finishtime});
        if (cursor.moveToNext()) { // 讲收入信息遍历到getaccount类里
            return new GetAccount(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("mark")),
                    cursor.getString(cursor.getColumnIndex("location")));
        }
        return null;
    }

    /**
     * 查找固定日期内收入之和
     */
    public double gettotal (String starttime, String finishtime) {
        db = helper.getWritableDatabase();
        int total = 0;
        Cursor cursor = db.rawQuery("select sum(money) as total_money from getaccount where time between ? and ?",
                new String[] {starttime, finishtime});
        if (cursor.moveToNext()) { // 将收入信息遍历到costaccount类里
            return cursor.getDouble(cursor.getColumnIndex("total_money"));
        } else {
            return 0.00;
        }
    }

    /**
     * 删除记录
     */
    public void delete(Integer... ids) {
        if(ids.length >0) { // 判断是否存在要删除的id
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<ids.length;i++) { // 遍历要删除的id集合
                sb.append('?').append(',');  // 将删除条件添加到StringBuffer对象中
            }
            sb.deleteCharAt(sb.length()-1); //去掉最后一个“,”字符
            db = helper.getWritableDatabase();
            db.execSQL("delete from getaccount where id in ("+ sb +")",
                    (Object[]) ids);
        }
    }

    /**
     * 获取收入信息
     */
    public List<GetAccount> getScrollDate(int start, int count) { //start 起始位置， count 每页显示数量
        List<GetAccount> getAccounts = new ArrayList<GetAccount>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from getaccount limit ?,?",
                new String[] {String.valueOf(start), String.valueOf(count)});
        while (cursor.moveToNext()) {
            getAccounts.add(new GetAccount(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("mark")),
                    cursor.getString(cursor.getColumnIndex("location"))));
        }
        return getAccounts;
    }

    /**
     * 获取总记录数
     */
    public long getCount() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(id) from getaccount", null); // 获取收入信息的记录数
        if (cursor.moveToNext()) {
            return cursor.getLong(0);
        }
        return 0;
    }

    /**
     * 获取收入最大编号
     */
    public int getMaxId() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select max(id) from getaccount", null);
        while (cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return 0;
    }
}
