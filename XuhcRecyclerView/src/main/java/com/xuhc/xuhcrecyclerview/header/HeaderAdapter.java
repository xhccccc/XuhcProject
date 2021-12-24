package com.xuhc.xuhcrecyclerview.header;

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
 * Created by Xuhc on 2021/12/17
 */

public class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;

    private int headerCount = 1;    // 头部 View 个数

    public HeaderAdapter(Context context) {
        mContext = context;
    }

    public void setHeaderDataList(List<String> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.header_header_recycle_item, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.header_content_recycle_item, parent, false);
            return new ContentViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).tvNum.setText(position + "");
            ((ContentViewHolder) holder).tvContent.setText(mList.get(position - headerCount));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (headerCount != 0 && position < headerCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return headerCount + getContentItemCount();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum, tvContent;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    /**
     * 内容长度
     */
    public int getContentItemCount() {
        return mList.size();
    }

    /**
     * 判断当前 item 是否是 HeadView
     */
    public boolean isHeaderView(int position) {
        return headerCount != 0 && position < headerCount;
    }
}
