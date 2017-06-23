package com.lost.utils;

import android.content.Context;

import com.google.gson.Gson;

/**
 * SharedPreferences帮助类，可以将对象转为json字符串存入SharedPreferences
 */
public class PreferencesHelper {

    public static final String KEY_USER_DATA = "current_user";

    private static final Gson mGson = new Gson();

    private PreferencesHelper() {
    }

    public static Context getContext() {
        return App.getApp();
    }

    public static <T> T get(String key, Class<T> clazz) {
        String value = PreferencesUtils.getString(getContext(), key);
        return mGson.fromJson(value, clazz);
    }

    public static <T> void put(String key, T object) {
        String value = mGson.toJson(object);
        PreferencesUtils.putString(getContext(), key, value);
    }

    public static void putString(String key, String value) {
        PreferencesUtils.putString(getContext(), key, value);
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return PreferencesUtils.getString(getContext(), key, defaultValue);
    }

    public static void putInt(String key, int value) {
        PreferencesUtils.putInt(getContext(), key, value);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        return PreferencesUtils.getInt(getContext(), key, defaultValue);
    }

    public static void putLong(String key, long value) {
        PreferencesUtils.putLong(getContext(), key, value);
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static long getLong(String key, long defaultValue) {
        return PreferencesUtils.getLong(getContext(), key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        PreferencesUtils.putFloat(getContext(), key, value);
    }

    public static float getFloat(String key) {
        return getFloat(key, 0);
    }

    public static float getFloat(String key, float defaultValue) {
        return PreferencesUtils.getFloat(getContext(), key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        PreferencesUtils.putBoolean(getContext(), key, value);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return PreferencesUtils.getBoolean(getContext(), key, defaultValue);
    }

    public static void clear() {
        PreferencesUtils.clear(getContext());
    }

    public static void remove(String key) {
        PreferencesUtils.remove(getContext(), key);
    }
}
