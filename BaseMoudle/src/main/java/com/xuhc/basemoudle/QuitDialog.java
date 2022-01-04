package com.xuhc.basemoudle;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class QuitDialog extends BaseDialog {

    private Button cancelBtn,confirmBtn;

    private OnQuitDialogBtnClickListener mOnQuitDialogBtnClickListener;

    public void setOnQuitDialogBtnClickListener(OnQuitDialogBtnClickListener onQuitDialogBtnClickListener) {
        mOnQuitDialogBtnClickListener = onQuitDialogBtnClickListener;
    }

    public interface OnQuitDialogBtnClickListener{
        void cancel();
        void confirm();
    }

    public QuitDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_quit_dialog;
    }

    @Override
    public void initView() {
        cancelBtn = findViewById(R.id.quit_btn_cancel);
        confirmBtn = findViewById(R.id.quit_btn_confirm);
        addListener();
    }

    private void addListener(){
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnQuitDialogBtnClickListener!= null){
                    mOnQuitDialogBtnClickListener.cancel();
                }
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnQuitDialogBtnClickListener != null){
                    mOnQuitDialogBtnClickListener.confirm();
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }
}