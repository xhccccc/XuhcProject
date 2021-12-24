package com.xhc.xhcnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhc.xhcnote.R;
import com.xhc.xhcnote.bean.UserInfo;
import com.xhc.xhcnote.tool.AnimUtils;
import com.xhc.xhcnote.view.NoteitemView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context mContext;
    private List<UserInfo> mUserInfoList;

    private static final int MODE_SELECT = 0;
    int mEditMode = MODE_SELECT;

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public List<UserInfo> getUserInfoList() {
        return mUserInfoList;
    }

    public OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void OnItemClick(int posi);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public NoteAdapter(Context context, List<UserInfo> list) {
        mContext = context;
        mUserInfoList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final UserInfo userInfo = mUserInfoList.get(position);
        holder.tv_content.setText(userInfo.getUserContent());
        holder.tv_time.setText(userInfo.getUserYearTime());

        if (mEditMode == MODE_SELECT){
            holder.iv_select.setVisibility(View.GONE);
        } else {
            holder.iv_select.setVisibility(View.VISIBLE);

            if (!userInfo.isSelect()){
                holder.iv_select.setImageResource(R.drawable.unselect);
            } else {
                holder.iv_select.setImageResource(R.drawable.select);
            }

        }

        holder.mNoteitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.OnItemClick(position);
                AnimUtils.setScaleAnimation(holder.mNoteitemView,300,mContext);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private NoteitemView mNoteitemView;
        private TextView tv_content;
        private TextView tv_time;
        private ImageView iv_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNoteitemView = (NoteitemView) itemView.findViewById(R.id.noteitem);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
        }

    }


}
