package com.example.personalaccount.entity;


/**
 * Created by Administrator on 2015/2/6.
 */
public class CostAccount {
    private int id;
    private double money;
    private String time;
    private String type;
    private String mark;
    private String location;

    public CostAccount (int id, double money, String time, String type, String mark, String location) {
        this.id = id;
        this.money = money;
        this.time = time;
        this.type = type;
        this.mark = mark;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {

        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMark() {

        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
