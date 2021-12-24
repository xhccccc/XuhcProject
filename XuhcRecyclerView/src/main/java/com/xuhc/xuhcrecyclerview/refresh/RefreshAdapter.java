package com.xuhc.xuhcrecyclerview.refresh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuhc.xuhcrecyclerview.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 下拉刷新 的 Adapter
 *
 * Created by Xuhc on 2021/12/17
 */

public class RefreshAdapter extends RecyclerView.Adapter<RefreshAdapter.RefreshViewHolder> {

    private static final String TAG = "xhccc" + RefreshAdapter.class.getSimpleName();

    private Context mContext;

    private List<RefreshDataBean.SubjectsBean> mList = new ArrayList<>();

    public RefreshAdapter(Context context) {
        mContext = context;
    }

    public void setRefreshDataList(List<RefreshDataBean.SubjectsBean> list) {
        mList = list;

        Log.d(TAG, "setRefreshDataList: " + mList.size());

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RefreshViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.refresh_recycle_item, parent, false);
        return new RefreshViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefreshViewHolder holder, int position) {
        holder.tvTitle.setText("片名：" + mList.get(position).getTitle());
        holder.tvMainlandPubdate.setText("日期：" + mList.get(position).getMainland_pubdate());
        holder.tvYear.setText("年代：" + mList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class RefreshViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvMainlandPubdate, tvYear;

        public RefreshViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvMainlandPubdate = itemView.findViewById(R.id.tv_mainland_pubdate);
            tvYear = itemView.findViewById(R.id.tv_year);
        }
    }
}
