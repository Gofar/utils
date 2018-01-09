package com.lost.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.support.annotation.ColorInt;
import android.support.design.widget.TabLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 给TabLayout创建指定宽高的Indicator
 *
 * @author lcf
 * @date 2017/12/7 17:37
 * @since 1.0
 */
public class TabIndicatorHelper {
    private Context mContext;
    private TabLayout mTabLayout;
    /**
     * 替换TabLayout的FrameLayout;
     */
    private FrameLayout mFrameLayout;
    /**
     * 相邻两个指示器间的偏移量
     */
    private int mStripOffset;
    /**
     * 当前选中的tab position
     */
    private int mSelectedPosition;

    public TabIndicatorHelper(Context context, TabLayout tabLayout) {
        this.mContext = context;
        this.mTabLayout = tabLayout;
        mSelectedPosition = mTabLayout.getSelectedTabPosition();
    }

    /**
     * 创建指示条
     *
     * @param widthDpValue  指示条宽
     * @param heightDpValue 指示条高
     * @param color         指示条颜色
     */
    public void createTabIndicator(int widthDpValue, int heightDpValue, @ColorInt int color) {
        checkTabLayoutMode();
        // 将TabLayout默认指示条高度置为0
        mTabLayout.setSelectedTabIndicatorHeight(0);
        // 替换
        replaceTabLayout();
        // 加入指示条ImageView
        ImageView imageView = new ImageView(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        params.gravity = Gravity.BOTTOM;
        mFrameLayout.addView(imageView, params);
        initStrip(imageView, widthDpValue, heightDpValue, color);
    }

    /**
     * 检查TabLayout的Mode
     */
    private void checkTabLayoutMode() {
        // TabLayout要设置为MODE_FIXED模式
        int tabMode = mTabLayout.getTabMode();
        if (tabMode != TabLayout.MODE_FIXED) {
            throw new IllegalArgumentException("TabLayout mode must be MODE_FIXED");
        }
    }

    /**
     * 替换TabLayout
     */
    private void replaceTabLayout() {
        if (mFrameLayout == null) {
            ViewGroup parent = (ViewGroup) mTabLayout.getParent();
            int index = 0;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (parent.getChildAt(i) == mTabLayout) {
                    index = i;
                    break;
                }
            }
            // 用FrameLayout替换TabLayout,并将TabLayout加入FrameLayout
            parent.removeView(mTabLayout);
            mFrameLayout = new FrameLayout(mContext);
            ViewGroup.LayoutParams lp = mTabLayout.getLayoutParams();
            parent.addView(mFrameLayout, index, lp);
            mFrameLayout.addView(mTabLayout);
        }
    }

    /**
     * 给指示条设置bitmap并监听TabLayout的tab切换
     *
     * @param imageView     指示条ImageView
     * @param widthDpValue  指示条宽
     * @param heightDpValue 指示条高
     * @param color         指示条颜色
     */
    private void initStrip(final ImageView imageView, int widthDpValue, int heightDpValue, @ColorInt int color) {
        // Tab数量
        int count = mTabLayout.getTabCount();
        int stripWidth = DimenUtil.dp2px(mContext, widthDpValue);
        int stripHeight = DimenUtil.dp2px(mContext, heightDpValue);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // Tab的宽度
        int tabWidth = metrics.widthPixels / count;
        // 指示条宽度不能超过Tab的宽度
        if (stripWidth > tabWidth) {
            stripWidth = tabWidth;
        }
        // 初始位置偏移量
        int offset = (tabWidth - stripWidth) / 2;
        // 初始化ImageView
        imageView.setImageBitmap(BitmapUtils.createColorBitmap(stripWidth, stripHeight, color));
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);

        mStripOffset = offset * 2 + stripWidth;
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // tab切换时指示条动画
                int position = tab.getPosition();
                Animation animation = new TranslateAnimation(mSelectedPosition * mStripOffset
                        , position * mStripOffset, 0, 0);
                animation.setDuration(300);
                animation.setFillAfter(true);
                animation.setInterpolator(new FastOutSlowInInterpolator());
                imageView.startAnimation(animation);
                mSelectedPosition = position;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 给Tab加间隔线
     *
     * @param heightDpValue divider高
     * @param color         divider颜色f
     */
    public void createTabDivider(int heightDpValue, @ColorInt int color) {
        replaceTabLayout();
        // Tab数量
        int count = mTabLayout.getTabCount();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // Tab的宽度
        int tabWidth = metrics.widthPixels / count;
        for (int i = 1; i < count; i++) {
            mFrameLayout.addView(createDividerView(heightDpValue, tabWidth * i, color));
        }
    }

    /**
     * 创建Tab间divider,默认宽度1px
     *
     * @param heightDpValue divider高
     * @param marginLeft    divider左间距
     * @param color         divider颜色
     * @return divider view
     */
    private View createDividerView(int heightDpValue, int marginLeft, @ColorInt int color) {
        View view = new View(mContext);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.width = 1;
        lp.height = DimenUtil.dp2px(mContext, heightDpValue);
        lp.leftMargin = marginLeft;
        lp.gravity = Gravity.CENTER_VERTICAL;
        view.setBackgroundColor(color);
        view.setLayoutParams(lp);
        return view;
    }
}
