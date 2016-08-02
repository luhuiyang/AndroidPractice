package com.example.personalaccount.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/3/13.
 */
public class TimeUtil {

    private static int mYear, mMonth, mDay, mHour, mMinute;

    public static String showNowTime(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        return mYear+"-"+(mMonth+1)+"-"+mDay+" "+mHour+"-"+mMinute;
    }

    public static String showStartTime(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        return mYear+"-"+(mMonth+1)+"-"+0+" "+0+"-"+0;
    }
    public static String[] splitDateAndTime(String s) {
        String[] result = s.split(" ");
        LogUtil.d("分割日期", result[1]);
        return result;
    }
    public static int[] getDate(String s){
        String[] mdate = s.split("-");
        int[] result = new int[3];
        result[0] = Integer.parseInt(mdate[0]);
        result[1] = Integer.parseInt(mdate[1]);
        result[2] = Integer.parseInt(mdate[2]);
        return result;
    }
    public static int[] getTime(String s){
        String[] mtime = s.split("-");
        int[] result = new int[2];
        result[0] = Integer.parseInt(mtime[0]);
        result[1] = Integer.parseInt(mtime[1]);
        return result;
    }

    //比较日期大小
    public static int timeCompare(String t1,String t2){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t1));
            c2.setTime(formatter.parse(t2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int result=c1.compareTo(c2);
        return result;
    }

    //比较String大小
    public static int DataCompare(String t1, String t2) {
        int result = t1.compareTo(t2);
        return result;
    }
}
