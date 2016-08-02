package com.example.personalaccount.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personalaccount.entity.CostAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/2/9.
 */
public class CostAccountDAO {
    private MyDatebaseHelper helper;
    private SQLiteDatabase db;
    public CostAccountDAO(Context context){
        helper = new MyDatebaseHelper(context);
    }

    /**
     * 添加支出信息
     */
    public void add(CostAccount costAccount) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into costaccount (id, money, time, type, mark, location) values (?,?,?,?,?,?)",
                new Object[] {costAccount.getId(), costAccount.getMoney(), costAccount.getTime(),
                costAccount.getType(), costAccount.getMark(), costAccount.getLocation()});
    }

    /**
     * 更新支出信息
     */
    public void update(CostAccount costAccount) {
        db = helper.getWritableDatabase();
        db.execSQL("update costaccount set money = ?, time = ?, type = ?, mark = ?, location = ? where id = ?",
                new Object[] {costAccount.getMoney(), costAccount.getTime(), costAccount.getType(),
                costAccount.getMark(), costAccount.getLocation(), costAccount.getId()});
    }

    /**
     * 查找支出信息
     */
    public CostAccount find(int id) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id, money, time, type, mark, location frome costaccount where id = ?",
                new String[] {String.valueOf(id)});
        if (cursor.moveToNext()) { // 将支出信息遍历到costaccount类里
            return new CostAccount(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("mark")),
                    cursor.getString(cursor.getColumnIndex("location")));
        }
        return null;
    }

    public List<CostAccount> find(String starttime, String finishtime) {
        List list=new ArrayList<CostAccount>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id, money, time, type, mark,location from costaccount where time between ? and ?",
                new String[] {starttime, finishtime});
        if (cursor.moveToNext()) { // 将支出信息遍历到costaccount类里
            list.add(new CostAccount(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("mark")),
                    cursor.getString(cursor.getColumnIndex("location"))));
            return list;
        }
        return null;
    }

    /**
     * 查找固定日期内消费之和
     */
    public double gettotal (String starttime, String finishtime) {
        db = helper.getWritableDatabase();
        int total = 0;
        Cursor cursor = db.rawQuery("select sum(money) as total_money from costaccount where time between ? and ?",
                new String[] {starttime, finishtime});
        if (cursor.moveToNext()) { // 将支出信息遍历到costaccount类里
            return cursor.getDouble(cursor.getColumnIndex("total_money"));
        } else {
            return 0.00;
        }
    }

    /**
     * 删除支出信息
     */
    public void delete(Integer... ids) {
        if (ids.length>0) {
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<ids.length;i++) {
                sb.append('?').append(',');
            }
            sb.deleteCharAt(sb.length()-1);
            db = helper.getWritableDatabase();
            db.execSQL("delete from costaccount where id in (" + sb + ")",
                    (Object[]) ids);
        }
    }

    /**
     * 获取支出信息
     */
    public List<CostAccount> getScrollData(int start, int count) {
        List<CostAccount> costAccounts = new ArrayList<CostAccount>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from costaccount limit ?,?",
                new String[] {String.valueOf(start), String.valueOf(count)});
        while (cursor.moveToNext()) {
            costAccounts.add(new CostAccount(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("mark")),
                    cursor.getString(cursor.getColumnIndex("location"))));
        }
        return costAccounts;
    }

    /**
     * 获取总记录数
     */
    public long getCount() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(id) from costaccount", null);
        if (cursor.moveToNext()) {
            return cursor.getLong(0);
        }
        return 0;
    }

    /**
     * 获取支出最大编号
     */
    public int getMaxId() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select max(id) from costaccount", null);
        while (cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return 0;
    }

}
