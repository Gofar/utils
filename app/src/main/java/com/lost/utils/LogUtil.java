package com.lost.utils;

import android.util.Log;

/**
 * Author: lcf
 * Description: Log工具
 * Since: 1.0
 * Date: 2017/7/4 13:59
 */
public class LogUtil {
    public static void v(String TAG, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }

}
