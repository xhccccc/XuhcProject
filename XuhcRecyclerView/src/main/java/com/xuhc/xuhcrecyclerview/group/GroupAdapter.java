package com.xuhc.xuhcrecyclerview.group;

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
 * 分组 的 Adapter
 *
 * Created by Xuhc on 2021/12/17
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private static final String TAG = "xhccc" + GroupAdapter.class.getSimpleName();

    private Context mContext;

    private List<GroupDataBean> mList = new ArrayList<>();

    public GroupAdapter(Context context) {
        mContext = context;
    }

    public void setGroupDataList(List<GroupDataBean> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.group_recycle_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.tvArea.setText(mList.get(position).getArea());
        holder.tvTeam.setText(mList.get(position).getTeam());

        if (position == 0) {
            holder.tvArea.setVisibility(View.VISIBLE);
        } else {
            if (mList.get(position).getArea().equals(mList.get(position - 1).getArea())) {
                holder.tvArea.setVisibility(View.GONE);
            } else {
                holder.tvArea.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView tvArea, tvTeam;

        public GroupViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tv_area);
            tvTeam = itemView.findViewById(R.id.tv_team);
        }
    }
}
