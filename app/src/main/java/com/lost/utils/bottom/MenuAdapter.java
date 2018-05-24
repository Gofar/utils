package com.lost.utils.bottom;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lost.utils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcf
 * @date 2018/5/24 14:31
 * @since 1.0
 */
public abstract class MenuAdapter<T> extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<T> mData;
    private OnRvItemClickListener mOnRvItemClickListener;

    public MenuAdapter(List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRvItemClickListener != null) {
                    mOnRvItemClickListener.onItemClick(MenuAdapter.this, v, holder.getLayoutPosition());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        T t = mData.get(position);
        holder.mTvText.setText(convert(t));
    }

    /**
     * 绑定数据
     *
     * @param t 数据
     * @return 显示的文字
     */
    public abstract String convert(T t);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    public List<T> getData() {
        return mData;
    }

    public void addNewData(List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }

    public void addData(T t) {
        this.mData.add(t);
        notifyItemInserted(mData.size());
    }

    public void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener) {
        mOnRvItemClickListener = onRvItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvText;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvText = itemView.findViewById(R.id.tv_text);
        }
    }

    public interface OnRvItemClickListener {
        void onItemClick(MenuAdapter adapter, View view, int position);
    }
}
