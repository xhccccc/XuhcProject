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
 * 双向滑动的子布局适配器 纵向滑动
 *
 * Created by Xuhc on 2021/12/17
 */

public class SlideVerticalAdapter extends RecyclerView.Adapter<SlideVerticalAdapter.SlideVerticalViewHolder> {

    private static final String TAG = "xhccc" + SlideVerticalAdapter.class.getSimpleName();

    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public SlideVerticalAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public SlideVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.slide_vertical_include_item, parent, false);
        return new SlideVerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideVerticalViewHolder holder, int position) {
        holder.tvText.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class SlideVerticalViewHolder extends RecyclerView.ViewHolder {

        TextView tvText;

        public SlideVerticalViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_slide_vertical_text);
        }
    }
}
