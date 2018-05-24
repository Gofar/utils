package com.lost.utils.bottom;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;

/**
 * @author lcf
 * @date 2018/5/23 17:50
 * @since 1.0
 */
public class CustomBottomDialog extends BottomSheetDialogFragment {

    private View mContentView;

    public static CustomBottomDialog newInstance() {
        return new CustomBottomDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext(), getTheme());
        if (mContentView != null) {
            dialog.setContentView(mContentView);
            setBehavior(mContentView);
        }
        return dialog;
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    /**
     * Behavior默认折叠，contentView太高会只显示一部分
     * 所以这里设置Behavior的PeekHeight
     *
     * @param contentView contentView
     */
    private void setBehavior(View contentView) {
        View parent = (View) contentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        contentView.measure(0, 0);
        behavior.setPeekHeight(contentView.getMeasuredHeight());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        parent.setLayoutParams(params);
    }
}