package com.example.personalaccount.entity;

/**
 * Created by Administrator on 2015/2/6.
 */
public class UserPassword {
    private String password;

    public UserPassword(){

    }

    public UserPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
