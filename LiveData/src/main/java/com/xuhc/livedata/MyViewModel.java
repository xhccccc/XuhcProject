package com.xuhc.livedata;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private static final String TAG = "xhccc" + MyViewModel.class.getSimpleName();

    private MutableLiveData<Long> liveData = new MutableLiveData<>();
    private MutableLiveData<String> liveData1 = new MutableLiveData<>();
    private MutableLiveData<String> liveData2 = new MutableLiveData<>();

    private MediatorLiveData<String> liveDataMerger;

    public MutableLiveData<Long> getLiveData() {
        return liveData;
    }

    public MutableLiveData<String> getLiveData1() {
        return liveData1;
    }

    public MutableLiveData<String> getLiveData2() {
        return liveData2;
    }

    public MediatorLiveData<String> getLiveDataMerger() {
        if (liveDataMerger == null) {
            liveDataMerger = new MediatorLiveData<>();
        }
        liveDataMerger.addSource(liveData1, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(TAG, "addSource1 onChanged: " + s);
                liveDataMerger.setValue(s);
            }
        });
        liveDataMerger.addSource(liveData2, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(TAG, "addSource2 onChanged: " + s);
                liveDataMerger.setValue(s);
            }
        });
        return liveDataMerger;
    }

    public void countDown() {
        new CountDownTimer(1 * 60 * 1000, 1 * 1000) {
            @Override
            public void onTick(long l) {
                Log.i(TAG, "onTick: " + l);
                liveData.postValue(l);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void mergeTest() {
        new CountDownTimer(1 * 60 * 1000, 3 * 1000) {
            @Override
            public void onTick(long l) {
                liveData1.postValue("网络有数据更新了" + l / 1000);
            }

            @Override
            public void onFinish() {

            }
        }.start();

        new CountDownTimer(1 * 60 * 1000, 10 * 1000) {
            @Override
            public void onTick(long l) {
                liveData2.postValue("本地数据库更新了" + l / 1000);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}