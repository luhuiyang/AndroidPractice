package com.example.personalaccount.utils;

import android.app.Application;
import android.content.Context;

/**
 * 全局获取Context
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
