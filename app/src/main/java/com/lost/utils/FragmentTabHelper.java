package com.lost.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Author: lcf
 * Description: 管理Fragment,切换当前显示的Fragment
 *              使用{@link FragmentTransaction#attach(Fragment)}和{@link FragmentTransaction#detach(Fragment)}
 *              管理Fragment，Fragment会相应调用{@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}和
 *              {@link Fragment#onDestroyView()}方法，需要Fragment缓存View避免重复创建。所以在这里使用
 *              {@link FragmentTransaction#show(Fragment)}和{@link FragmentTransaction#hide(Fragment)}来切换Fragment,
 *              此时Fragment会调用{@link Fragment#onHiddenChanged(boolean)},在这个方法里判断Fragment是否显示。
 * Since: 1.0
 * Date: 2017/6/15 16:53
 */
public class FragmentTabHelper {
    private final ArrayList<TabInfo> mTabs = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private Context mContext;
    private int mContainerId;
    private TabInfo mLastTab;

    public FragmentTabHelper(Context context, FragmentManager fragmentManager, int containerId) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = containerId;
    }

    /**
     * 添加Fragment
     *
     * @param tag tag
     * @param clss class
     * @param args bundle
     */
    public void addTab(String tag, Class<?> clss, Bundle args) {
        TabInfo info = new TabInfo(tag, clss, args);
        info.fragment = mFragmentManager.findFragmentByTag(tag);
        if (info.fragment != null && !info.fragment.isDetached()) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.detach(info.fragment);
            ft.commit();
        }
        mTabs.add(info);
    }

    /**
     * 设置当前显示的Fragment
     *
     * @param tag tag
     */
    public void setCurrentFragment(String tag) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        TabInfo newTab = getTabInfoForTag(tag);
        if (mLastTab != newTab) {
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.hide(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(mContext, newTab.clss.getName(), newTab.args);
                    ft.add(mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.show(newTab.fragment);
                }
            }
            mLastTab = newTab;
        }
        ft.commit();

    }

    /**
     * 返回当前Fragment的tag
     *
     * @return String
     */
    public String getCurrentFragmentTag() {
        if (mLastTab != null) {
            return mLastTab.tag;
        } else {
            return null;
        }
    }

    @Nullable
    private TabInfo getTabInfoForTag(String tabId) {
        for (int i = 0, count = mTabs.size(); i < count; i++) {
            final TabInfo tab = mTabs.get(i);
            if (tab.tag.equals(tabId)) {
                return tab;
            }
        }
        return null;
    }

    static final class TabInfo {
        final @NonNull
        String tag;
        final @NonNull
        Class<?> clss;
        final @Nullable
        Bundle args;
        Fragment fragment;

        TabInfo(@NonNull String _tag, @NonNull Class<?> _class, @Nullable Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }
}
