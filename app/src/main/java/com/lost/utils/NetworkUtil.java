package com.lost.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @Description: 网络状况工具类
 * @Author: Lost
 * @Since: 1.0
 * @Date: 2017/3/1517:41
 */
public class NetworkUtil {

    /**
     * 判断是否有网络连接
     * <p>This method requires the caller to hold the permission
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
     *
     * @param context 上下文对象
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo[] infos = cm.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断Wifi是否连接
     * <p>This method requires the caller to hold the permission
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
     *
     * @param context 上下文对象
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isConnected();
    }

    /**
     * 判断mobile网络是否连接
     * <p>This method requires the caller to hold the permission
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
     *
     * @param context 上下文对象
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 获取当前网络连接类型
     * <p>This method requires the caller to hold the permission
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
     *
     * @param context 上下文对象
     * @return
     */
    public static int getConnectedType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return -1;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return info.getType();
        }
        return -1;
    }

    /**
     * 判断网络类型
     * @param context
     * @return
     */
    public static NetType getAPNType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return NetType.NON_NET;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_MOBILE) {
                if (info.getExtraInfo().toLowerCase().equals("cmmet")) {
                    return NetType.CMMET;
                } else {
                    return NetType.CMWAP;
                }
            } else if (type == ConnectivityManager.TYPE_WIFI) {
                return NetType.WIFI;
            }
        }
        return NetType.NON_NET;
    }

    public enum NetType {
        NON_NET,    // 无网络
        WIFI,       // 使用WIFI上网
        CMMET,      // CMNET上网
        CMWAP       // CMWAP（中国移动代理）上网
    }
}
