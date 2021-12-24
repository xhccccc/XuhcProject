package com.xuhc.xuhcrecyclerview.slide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhc.xuhcrecyclerview.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 双向滑动 的 Adapter
 *
 * Created by Xuhc on 2021/12/17
 */

public class SlideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "xhccc" + SlideAdapter.class.getSimpleName();

    private static final int TYPE_HORIZONTAL = 0;
    private static final int TYPE_VERTICAL = 1;

    private Context mContext;
    private List<Integer> mTypeList = new ArrayList<>();

    private List<String> mHorizontalList = new ArrayList<>();
    private List<String> mVerticalList = new ArrayList<>();

    public SlideAdapter(Context context, List<Integer> typeList) {
        mContext = context;
        mTypeList = typeList;
    }

    public void setHorizontalDataList(List<String> horizontalDataList) {
        mHorizontalList = horizontalDataList;

        notifyDataSetChanged();
    }

    public void setVerticalDataList(List<String> verticalDataList) {
        mVerticalList = verticalDataList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mTypeList.get(position) == 0) {         // 横向
            return TYPE_HORIZONTAL;
        } else if (mTypeList.get(position) == 1) {  // 纵向
            return TYPE_VERTICAL;
        } else {
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HORIZONTAL) {
            View viewHorizontal = LayoutInflater.from(mContext).inflate(R.layout.slide_horizontal_include, parent, false);
            return new HorizontalViewHolder(viewHorizontal);
        } else if (viewType == TYPE_VERTICAL) {
            View viewVertical = LayoutInflater.from(mContext).inflate(R.layout.slide_vertical_include, parent, false);
            return new VerticalViewHolder(viewVertical);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HorizontalViewHolder) {
            if (mHorizontalList != null) {
                SlideHorizontalAdapter horizontalAdapter = new SlideHorizontalAdapter(mContext, mHorizontalList);
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ((HorizontalViewHolder) holder).rcvHorizontal.setLayoutManager(manager);
                ((HorizontalViewHolder) holder).rcvHorizontal.setHasFixedSize(true);
                ((HorizontalViewHolder) holder).rcvHorizontal.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
                ((HorizontalViewHolder) holder).rcvHorizontal.setAdapter(horizontalAdapter);

                horizontalAdapter.notifyDataSetChanged();
            }
        } else if (holder instanceof VerticalViewHolder) {
            if (mVerticalList != null) {
                SlideVerticalAdapter verticalAdapter = new SlideVerticalAdapter(mContext, mVerticalList);
                ((VerticalViewHolder) holder).rcvVertical.setLayoutManager(new LinearLayoutManager(mContext));
                ((VerticalViewHolder) holder).rcvVertical.setHasFixedSize(true);
                ((VerticalViewHolder) holder).rcvVertical.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                ((VerticalViewHolder) holder).rcvVertical.setAdapter(verticalAdapter);

                verticalAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    public static class HorizontalViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rcvHorizontal;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            rcvHorizontal = itemView.findViewById(R.id.rv_slide_horizontal);
        }
    }

    public static class VerticalViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rcvVertical;

        public VerticalViewHolder(View itemView) {
            super(itemView);
            rcvVertical = itemView.findViewById(R.id.rv_slide_vertical);
        }
    }
}
