package com.lost.utils.bottom;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author lcf
 * @date 2018/5/24 15:23
 * @since 1.0
 */
public interface OnRvItemClickListener {
    void onItemClick(RecyclerView.Adapter adapter, View view, int position);
}
