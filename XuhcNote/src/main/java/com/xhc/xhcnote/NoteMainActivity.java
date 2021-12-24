package com.xhc.xhcnote;

import android.content.Intent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xhc.xhcnote.action.AddActivity;
import com.xhc.xhcnote.action.SearchActivity;
import com.xhc.xhcnote.adapter.NoteAdapter;
import com.xhc.xhcnote.bean.UserInfo;
import com.xhc.xhcnote.dialog.DeleteTipDialog;
import com.xhc.xhcnote.tool.AnimUtils;
import com.xhc.xhcnote.tool.NoteSQLite;
import com.xhc.xhcnote.tool.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteMainActivity extends BaseActivity implements View.OnClickListener, NoteAdapter.OnItemClickListener {

    private int index = 0;
    private static final int MODE_SELECT = 0;
    private static final int MODE_EDIT = 1;
    private int mEditMode = MODE_SELECT;
    private boolean editorStatus = false;

    private NoteSQLite mNoteSQLite;

    private RelativeLayout rl_main_header;
    private ImageView iv_edit;
    private ImageView iv_search;

    private boolean isSelectAll = false;
    private RelativeLayout rl_main_header_select;
    private ImageView iv_cancel;
    private ImageView iv_delete;
    private TextView tv_select_num;
    private ImageView iv_checkall;

    private RecyclerView mRecyclerView;
    private NoteAdapter mNoteAdapter;
    private List<UserInfo> mUserInfoList = new ArrayList<>();

    private RelativeLayout rl_add;
    private ImageView add;

    @Override
    protected int getRes() {
        return R.layout.activity_note_main;
    }

    protected void initView(){
        mNoteSQLite = new NoteSQLite(this);

        rl_main_header = (RelativeLayout) findViewById(R.id.rl_main_header);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_search = (ImageView) findViewById(R.id.iv_search);

        rl_main_header_select = (RelativeLayout) findViewById(R.id.rl_main_header_select);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        tv_select_num = (TextView) findViewById(R.id.tv_select_num);
        iv_checkall = (ImageView) findViewById(R.id.iv_checkall);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        rl_add = (RelativeLayout) findViewById(R.id.rl_add);
        rl_add.getBackground().setAlpha(0);
        add = (ImageView) findViewById(R.id.add);
    }

    protected void initData(){

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        mRecyclerView.setLayoutAnimation(controller);

        mUserInfoList = mNoteSQLite.query();

        mNoteAdapter = new NoteAdapter(this,mUserInfoList);
        mNoteAdapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(mNoteAdapter);

    }

    protected void setListener(){
        iv_edit.setOnClickListener(this);
        iv_search.setOnClickListener(this);

        iv_cancel.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        iv_checkall.setOnClickListener(this);

        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_edit || id == R.id.iv_cancel) {
            updateEditor();
        } else if (id == R.id.iv_search) {
            startActivityForResult(new Intent(NoteMainActivity.this, SearchActivity.class), 1);
        } else if (id == R.id.iv_delete) {
            deleteEditor();
        } else if (id == R.id.iv_checkall) {
            selectALLorRE();
        } else if (id == R.id.add) {
            AnimUtils.addanimation(add, rl_add);
            startActivityForResult(new Intent(NoteMainActivity.this, AddActivity.class), 1);
        }
    }

    @Override
    public void OnItemClick(int posi) {
        if (editorStatus){
            UserInfo userInfo = mUserInfoList.get(posi);
            boolean isSelect = userInfo.isSelect();
            if (!isSelect){
                index++;
                userInfo.setSelect(true);
            } else {
                index--;
                userInfo.setSelect(false);
            }
            setSelectNUM(index);
            setBgforindex(index);
            mNoteAdapter.notifyDataSetChanged();
//            ToastUtil.ShowShort(this,"posi: " + posi);
        } else {
            UserInfo userInfo = mUserInfoList.get(posi);
            Intent intent = new Intent(NoteMainActivity.this,AddActivity.class);
            intent.putExtra("id",userInfo.getId());
            intent.putExtra("content",userInfo.getUserContent());
            intent.putExtra("time",userInfo.getUserYearTime());
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == 2){
                initData();
            }
        }
    }

    public void updateEditor(){
        mEditMode = mEditMode == MODE_SELECT ? MODE_EDIT : MODE_SELECT;
        if (mEditMode == MODE_EDIT) {
            rl_main_header.setVisibility(View.GONE);
            rl_main_header_select.setVisibility(View.VISIBLE);
            add.setVisibility(View.GONE);
            editorStatus = true;
        } else {
            rl_main_header.setVisibility(View.VISIBLE);
            rl_main_header_select.setVisibility(View.GONE);
            add.setVisibility(View.VISIBLE);
            editorStatus = false;
            cleanAll();
        }
        setSelectNUM(0);
        setBgforindex(0);
        mNoteAdapter.setEditMode(mEditMode);
        mRecyclerView.scrollToPosition(0);
    }

    private void setSelectNUM(int index){
        String num = String.format(getString(R.string.select_num),index);
        tv_select_num.setText(num);
    }


    /**
     * 根据index设置删除按钮是否可点击
     * @param index
     */
    private void setBgforindex(int index){
        if (index != 0){
            iv_delete.setImageResource(R.drawable.delete);
            iv_delete.setEnabled(true);
        } else {
            iv_delete.setImageResource(R.drawable.undelete);
            iv_delete.setEnabled(false);
        }
    }

    private void cleanAll(){
        for (int i = 0; i<mNoteAdapter.getUserInfoList().size(); i++){
            mNoteAdapter.getUserInfoList().get(i).setSelect(false);
        }
        index = 0;
        setSelectNUM(index);
        setBgforindex(index);
        iv_checkall.setImageResource(R.drawable.uncheckall);
        isSelectAll = false;
    }

    private void deleteEditor(){
        if (index != 0){
            final DeleteTipDialog deleteTipDialog = new DeleteTipDialog(NoteMainActivity.this);
            deleteTipDialog.show();
            if (index == 1){
                deleteTipDialog.setDialogContent(getString(R.string.deleteone));
            } else {
                String num = String.format(getString(R.string.selectdelete),index);
                deleteTipDialog.setDialogContent(num);
            }
            deleteTipDialog.setOnCallbackListener(new DeleteTipDialog.OnCallbackListener() {
                @Override
                public void OnCallback(boolean confirm) {
                    if (confirm){
                        for (int i = 0; i<mNoteAdapter.getUserInfoList().size(); i++){
                            if (mNoteAdapter.getUserInfoList().get(i).isSelect()){
                                mNoteSQLite.deleteData(mNoteAdapter.getUserInfoList().get(i).getId());
                                ToastUtil.ShowShort(NoteMainActivity.this,"删除成功");
                            }
                        }
                        updateEditor();
                        initData();
                    }
                }
            });
        } else {
            ToastUtil.ShowShort(this,"没有要删除的选项");
        }
    }

    private void selectALLorRE(){
        if (!isSelectAll){
            for (int i = 0; i<mNoteAdapter.getUserInfoList().size(); i++){
                mNoteAdapter.getUserInfoList().get(i).setSelect(true);
            }
            index = mNoteAdapter.getUserInfoList().size();
            iv_checkall.setImageResource(R.drawable.checkall);
            isSelectAll = true;
        } else {
            for (int i = 0; i<mNoteAdapter.getUserInfoList().size(); i++){
                mNoteAdapter.getUserInfoList().get(i).setSelect(false);
            }
            index = 0;
            iv_checkall.setImageResource(R.drawable.uncheckall);
            isSelectAll = false;
        }
        setSelectNUM(index);
        setBgforindex(index);
        mNoteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (editorStatus){
            updateEditor();
        } else {
            super.onBackPressed();
        }
    }

    private void Rerefresh(){
        mNoteAdapter.notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }
}
