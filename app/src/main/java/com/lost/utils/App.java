package com.lost.utils;

import android.app.Application;

/**
 * Author: lcf
 * Description:
 * Since: 1.0
 * Date: 2017/6/23 15:49
 */
public class App extends Application {
    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
    }

    public static App getApp() {
        return mApp;
    }
}
