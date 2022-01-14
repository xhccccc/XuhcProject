package com.xuhc.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;
import com.xuhc.livedata.databinding.ActivityLiveDataBinding;

/**
 * 参考链接
 * https://www.jianshu.com/p/e975e71944de
 * https://www.jianshu.com/p/982545e01d0a
 * https://developer.android.google.cn/topic/libraries/architecture/viewmodel?hl=zh-cn
 * https://www.jianshu.com/p/6e7e05a8b750 LiveData
 */

public class LiveDataActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + LiveDataActivity.class.getSimpleName();

    private ActivityLiveDataBinding mLiveDataBinding;

    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_live_data);

        mLiveDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_live_data);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
//        viewModel.countDown();
        viewModel.getLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mLiveDataBinding.tvLiveDataTest.setText(String.valueOf(aLong/1000));
            }
        });

        viewModel.getLiveDataMerger().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(TAG, "liveDataMerger onChanged: " + s);
                mLiveDataBinding.tvLiveDataTest.setText(s);
            }
        });
        viewModel.mergeTest();
    }
}