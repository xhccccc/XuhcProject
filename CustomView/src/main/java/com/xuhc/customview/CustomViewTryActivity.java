package com.xuhc.customview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.xuhc.customview.customtoast.ToastUtils;
import com.xuhc.customview.seekbar.SeekSettingView;
import com.xuhc.utils.log.LogUtil;

public class CustomViewTryActivity extends AppCompatActivity implements View.OnClickListener
    , CompoundButton.OnCheckedChangeListener{

    private static final String TAG = "xhccc" + CustomViewTryActivity.class.getSimpleName();

    private SwitchCompat mSwitchCompat;
    private SeekSettingView mSeekSettingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_try);

        findViewById(R.id.bt_custom_toast).setOnClickListener(this);
        mSwitchCompat = findViewById(R.id.switch_compat);
        mSwitchCompat.setOnCheckedChangeListener(this);
        mSeekSettingView = findViewById(R.id.custom_seek_bar);

        addListener();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_custom_toast){
            ToastUtils.showCustomToast(this,"测试自定义Toast");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ToastUtils.showShortToast(this,"boolean: " + isChecked);
    }

    private void addListener(){
        mSeekSettingView.setOnSeekListener(new SeekSettingView.OnSeekListener() {
            @Override
            public void onSeek(View view, int value) {
                LogUtil.p(TAG,String.valueOf(value));
            }
        });
    }
}