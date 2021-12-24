package com.xhc.xhcnote.action;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhc.xhcnote.BaseActivity;
import com.xhc.xhcnote.R;
import com.xhc.xhcnote.dialog.DeleteTipDialog;
import com.xhc.xhcnote.tool.NoteSQLite;
import com.xhc.xhcnote.tool.ToastUtil;
import com.xhc.xhcnote.tool.UtilDB;

public class AddActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_cancel;
    private TextView tv_add_time;
    private ImageView iv_add_delete;
    private ImageView iv_upchange;

    private EditText et_note_content;
    private String id;
    private String content;

    private NoteSQLite mNoteSQLite;

    @Override
    protected int getRes() {
        return R.layout.activity_add;
    }

    protected void initView(){
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        tv_add_time = (TextView) findViewById(R.id.tv_add_time);
        iv_add_delete = (ImageView) findViewById(R.id.iv_add_delete);
        iv_upchange = (ImageView) findViewById(R.id.iv_upchange);

        et_note_content = (EditText) findViewById(R.id.et_note_content);
    }

    protected void initData(){
        mNoteSQLite = new NoteSQLite(this);
        Intent intent = getIntent();
        if (intent != null){
            id = intent.getStringExtra("id");
            if (id != null){
                String time = intent.getStringExtra("time");
                content = intent.getStringExtra("content");
                iv_add_delete.setImageResource(R.drawable.delete);
                tv_add_time.setText(time);
                et_note_content.setText(content);
            } else {
                iv_add_delete.setImageResource(R.drawable.undelete);
            }
        }
    }

    protected void setListener(){
        iv_cancel.setOnClickListener(this);
        iv_add_delete.setOnClickListener(this);
        iv_upchange.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.iv_cancel) {
            onBackPressed();
        } else if (viewId == R.id.iv_add_delete) {
            delete();
        } else if (viewId == R.id.iv_upchange) {
            insertOrupdate();
            closeSolfKeyboard(view);
        }
    }

    private void insertOrupdate(){
        if (id != null){
            String edcontent = et_note_content.getText().toString().trim();
            if (!edcontent.equals(content)){
                if (edcontent.length() > 0){
                    if (mNoteSQLite.updateData(id,edcontent,UtilDB.showTime())){
                        ToastUtil.ShowShort(this,"修改已保存");
                        setResult(2);
                        finish();
                    } else {
                        ToastUtil.ShowShort(this,"修改失败");
                    }
                } else {
                    ToastUtil.ShowShort(this,"修改内容不能为空，或直接删除");
                }
            } else {
                ToastUtil.ShowShort(this,"没有改变内容");
            }
        } else {
            String edcontent = et_note_content.getText().toString().trim();
            if (edcontent.length() > 0){
                if (mNoteSQLite.insertDate(edcontent, UtilDB.showTime())){
                    ToastUtil.ShowShort(this,"添加成功");
                    setResult(2);
                    finish();
                } else {
                    ToastUtil.ShowShort(this,"添加失败");
                }
            } else {
                ToastUtil.ShowShort(this,"添加内容不能为空");
            }
        }
    }

    private void closeSolfKeyboard(View view){
        InputMethodManager manager = ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null){
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void delete(){
        if (id != null){
            DeleteTipDialog deleteTipDialog = new DeleteTipDialog(this);
            deleteTipDialog.show();
            deleteTipDialog.setDialogContent(getString(R.string.deleteone));
            deleteTipDialog.setOnCallbackListener(new DeleteTipDialog.OnCallbackListener() {
                @Override
                public void OnCallback(boolean confirm) {
                    if (confirm){
                        mNoteSQLite.deleteData(id);
                        setResult(2);
                        finish();
                        ToastUtil.ShowShort(AddActivity.this,"删除成功");
                    }
                }
            });
        } else {
            ToastUtil.ShowShort(this,"没有内容可操作");
        }
    }

    @Override
    public void onBackPressed() {
        insertOrupdate();
        super.onBackPressed();
    }
}
