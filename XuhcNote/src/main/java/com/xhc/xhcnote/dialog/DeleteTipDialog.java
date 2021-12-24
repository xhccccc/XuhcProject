package com.xhc.xhcnote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xhc.xhcnote.R;

import androidx.annotation.NonNull;

public class DeleteTipDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private TextView dia_content;
    private Button bt_confirm;
    private Button bt_cancel;

    public TextView getDia_content() {
        return dia_content;
    }

    public void setDialogContent(String content){
        if (content != null){
            getDia_content().setText(content);
        }

    }

    public OnCallbackListener mOnCallbackListener;
    public interface OnCallbackListener{
        void OnCallback(boolean confirm);
    }
    public void setOnCallbackListener(OnCallbackListener onCallbackListener) {
        mOnCallbackListener = onCallbackListener;
    }

    public DeleteTipDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog);
        initView();
        setListener();
    }

    private void initView(){
        dia_content = (TextView) findViewById(R.id.dia_content);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
    }

    private void setListener(){
        bt_confirm.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_confirm) {
            mOnCallbackListener.OnCallback(true);
            dismiss();
        } else if (id == R.id.bt_cancel) {
            mOnCallbackListener.OnCallback(false);
            dismiss();
        }
    }

    @Override
    public void show() {
        super.show();

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0,0,0,0);

        getWindow().setAttributes(layoutParams);

    }
}
