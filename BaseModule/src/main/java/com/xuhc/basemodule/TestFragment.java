package com.xuhc.basemodule;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class TestFragment extends BaseFragment implements View.OnClickListener {

    private Button btQuitDialog;
    private Button btPopWindow;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_base;
    }

    @Override
    public void initView(View view) {
        btQuitDialog = view.findViewById(R.id.bt_quit_dialog);
        btPopWindow = view.findViewById(R.id.bt_pop_window);
    }

    @Override
    public void initFocus() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        btQuitDialog.setOnClickListener(this);
        btPopWindow.setOnClickListener(this);
    }

    @Override
    public void detach() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void pageSelect() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_quit_dialog) {
            QuitDialog quitDialog = new QuitDialog(getContext());
            quitDialog.setOnQuitDialogBtnClickListener(new QuitDialog.OnQuitDialogBtnClickListener() {
                @Override
                public void cancel() {
                    quitDialog.dismiss();
                }

                @Override
                public void confirm() {
                    quitDialog.dismiss();
                }
            });
            quitDialog.show();
        } else if (id == R.id.bt_pop_window) {
            SettingPopWindow settingPopWindow = new SettingPopWindow(mContext);
            settingPopWindow.showAsDropDown(btPopWindow, 0, 0, Gravity.TOP | Gravity.START);
        }
    }
}
