package com.example.personalaccount.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by Administrator on 2015/3/12.
 */
public class Accounts implements Comparable<Accounts> ,Serializable {
    private int id;
    private double money;
    private String time;
    private String type;
    private String mark;
    private String location;

    public Accounts(int id, double money, String time, String type, String mark, String location) {
        this.id = id;
        this.money = money;
        this.time = time;
        this.type = type;
        this.mark = mark;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getMark() {

        return mark;
    }

    public String getType() {

        return type;
    }

    public String getTime() {

        return time;
    }

    public double getMoney() {

        return money;
    }

    public int getId() {

        return id;
    }

    @Override
    public int compareTo(Accounts another) {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddHH-mm");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(this.getTime()));
            c2.setTime(formatter.parse(another.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int result = c2.compareTo(c1);
        return result;
    }

}
