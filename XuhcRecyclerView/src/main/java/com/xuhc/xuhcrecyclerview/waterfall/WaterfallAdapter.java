package com.xuhc.xuhcrecyclerview.waterfall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xuhc.xuhcrecyclerview.R;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 瀑布流 的 Adapter
 *
 * Created by Xuhc on 2021/12/17
 */

public class WaterfallAdapter extends RecyclerView.Adapter<WaterfallAdapter.WaterfallViewHolder> {

    private static final String TAG = "xhccc" + WaterfallAdapter.class.getSimpleName();

    private Context mContext;

    private List<WaterfallBean.ResultsBean> mList;

    public WaterfallAdapter(Context context) {
        mContext = context;
    }

    public void setWaterfallData(List<WaterfallBean.ResultsBean> list) {
        mList = list;

        Log.d(TAG, "setWaterfallData: " + mList.size());

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaterfallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.waterfall_recycle_item, parent, false);
        return new WaterfallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterfallViewHolder holder, int position) {
        Glide.with(mContext).load(mList.get(position).getUrl()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class WaterfallViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public WaterfallViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
}
