package com.lost.utils.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lost.utils.R;

/**
 * @author lcf
 * @date 2018/5/24 10:06
 * @since 1.0
 */
public class BottomListDialog extends BottomSheetDialogFragment {

    private RecyclerView mRvMenu;
    private TextView mTvCancel;
    private MenuAdapter mAdapter;
    private CharSequence mCancelText = "取消";
    private OnMenuItemClickListener mOnMenuItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_bottom_list, container, false);
        mRvMenu = view.findViewById(R.id.rv_menu);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            setBehavior(view);
        }
        // 将默认白色背景改为透明
        getDialog().getWindow().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(Color.TRANSPARENT);
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

    private void init() {
        mRvMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvMenu.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        if (mAdapter != null) {
            mRvMenu.setAdapter(mAdapter);
            mAdapter.setOnRvItemClickListener(new MenuAdapter.OnRvItemClickListener() {
                @Override
                public void onItemClick(MenuAdapter adapter, View view, int position) {
                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.onItemClick(adapter, position);
                    }
                    dismiss();
                }
            });
        }
        mTvCancel.setText(mCancelText);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setAdapter(MenuAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void setCancelText(CharSequence cancelText) {
        this.mCancelText = cancelText;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public interface OnMenuItemClickListener {
        void onItemClick(MenuAdapter adapter, int position);
    }
}
