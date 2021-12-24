package com.xuhc.xuhcrecyclerview.expandcollapse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuhc.xuhcrecyclerview.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 展开和收缩 的 Adapter
 *
 * Created by Xuhc on 2021/12/17
 */

public class ExpandCollapseAdapter extends RecyclerView.Adapter<ExpandCollapseAdapter.ExpandCollapseViewHolder> {

    private static final String TAG = "xhccc" + ExpandCollapseAdapter.class.getSimpleName();

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    private int expandedPosition = -1;

    private ExpandCollapseViewHolder mViewHolder;

    public ExpandCollapseAdapter(Context context) {
        mContext = context;
    }

    public void setExpandCollapseDataList(List<String> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpandCollapseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.expandcollapse_recycle_item, parent, false);
        return new ExpandCollapseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExpandCollapseViewHolder holder, int position) {
        holder.tvTeam.setText(mList.get(position));
        holder.tvTeamChild.setText(mList.get(position) + "的子内容");

        final boolean isExpanded = position == expandedPosition;
        holder.rlChild.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewHolder != null) {
                    mViewHolder.rlChild.setVisibility(View.GONE);
                    notifyItemChanged(expandedPosition);
                }
                expandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                mViewHolder = isExpanded ? null : holder;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ExpandCollapseViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlParent, rlChild;
        TextView tvTeam, tvTeamChild;

        public ExpandCollapseViewHolder(View itemView) {
            super(itemView);
            rlParent = itemView.findViewById(R.id.rl_parent);
            rlChild = itemView.findViewById(R.id.rl_child);
            tvTeam = itemView.findViewById(R.id.tv_team);
            tvTeamChild = itemView.findViewById(R.id.tv_team_child);
        }
    }
}
