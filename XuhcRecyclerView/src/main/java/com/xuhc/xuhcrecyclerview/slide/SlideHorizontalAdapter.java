package com.xuhc.xuhcrecyclerview.slide;

import android.content.Context;
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
 * 双向滑动的子布局适配器 横向滑动
 *
 * Created by Xuhc on 2021/12/17
 */

public class SlideHorizontalAdapter extends RecyclerView.Adapter<SlideHorizontalAdapter.SlideHorizontalViewHolder> {

    private static final String TAG = "xhccc" + SlideHorizontalViewHolder.class.getSimpleName();

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    public SlideHorizontalAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public SlideHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.slide_horizontal_include_item, parent, false);
        return new SlideHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideHorizontalViewHolder holder, int position) {
        holder.tvText.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class SlideHorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView tvText;

        public SlideHorizontalViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_slide_horizontal_text);
        }
    }
}
