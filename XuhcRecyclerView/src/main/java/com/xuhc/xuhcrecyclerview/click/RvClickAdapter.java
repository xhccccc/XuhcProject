package com.xuhc.xuhcrecyclerview.click;

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
 * Item 点击对应的 Adapter
 *
 * Created by Xuhc on 2021/12/17
 */

public class RvClickAdapter extends RecyclerView.Adapter<RvClickAdapter.RvClickViewHolder> {

    private static final String TAG = "xhccc" + RvClickAdapter.class.getSimpleName();

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String content);
    }

    public RvClickAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void setRcvClickDataList(List<String> list) {
        Log.d(TAG, "setRvClickDataList: " + list.size());
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RvClickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_click_recycle_item, parent, false);
        return new RvClickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvClickViewHolder holder, int position) {
        final String content = mList.get(position);

        holder.tvNum.setText(position + 1 + "");
        holder.tvContent.setText(content);

        // 第一种写法：直接在 Adapter 里写
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "你点击的是：" + content, Toast.LENGTH_SHORT).show();
//            }
//        });

        // 第二种写法：将点击事件传到 Activity 里去写
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onItemClick(content);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class RvClickViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum, tvContent;

        public RvClickViewHolder(View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
