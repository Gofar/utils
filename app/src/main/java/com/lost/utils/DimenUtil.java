package com.lost.utils;

import android.content.Context;

/**
 * @Description: dp/sp、px转换
 * @Author: Lost
 * @Since: 1.0
 * @Date: 2017/3/1516:18
 */
public class DimenUtil {

    public static int px2dp(Context context, int pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue * density + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue / density + 0.5f);
    }

    public static int px2sp(Context context, int pxValue) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue * scaleDensity + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue / scaleDensity + 0.5f);
    }

//    public static float sp2px(float spValue, int type) {
//        switch (type) {
//            case CHINESE:
//                return spValue * scaledDensity;
//            case NUMBER_OR_CHARACTER:
//                return spValue * scaledDensity * 10.0f / 18.0f;
//            default:
//                return spValue * scaledDensity;
//        }
//    }
}
