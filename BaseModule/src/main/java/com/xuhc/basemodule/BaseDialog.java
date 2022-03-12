package com.xuhc.basemodule;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;


public abstract class BaseDialog extends Dialog {
    protected Context mContext;

    public BaseDialog(Context context) {
        super(context, R.style.NoTitleDialogStyle);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            initView();
        } else {
            throw new NullPointerException("Layout is null");
        }
    }

    @Override
    public void show() {
        /**
         * 设置type
         */
//        getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }



    /**
     * 布局ID
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化view
     */
    public abstract void initView();
}