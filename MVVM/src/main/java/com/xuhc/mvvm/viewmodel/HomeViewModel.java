package com.xuhc.mvvm.viewmodel;

import android.util.Log;

import com.xuhc.mvvm.model.HomeModel;
import com.xuhc.mvvm.model.impl.HomeModelImpl;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel implements OnHomeListener {

    private static final String TAG = "xhccc" + HomeViewModel.class.getSimpleName();

    private HomeModel mHomeModel;

    public HomeViewModel() {
        mHomeModel = new HomeModelImpl();
    }

    private MutableLiveData<Long> liveData = new MutableLiveData<>();


    public MutableLiveData<Long> getLiveData() {
        return liveData;
    }

    public void startCountDown(){
        mHomeModel.getCountDownNumber(liveData,this);
    }

    @Override
    public void onTickSucceed(long l) {
        Log.i(TAG, "onTick: " + l);
        liveData.postValue(l);
    }

    @Override
    public void onTickFinish() {
        Log.i(TAG, "onFinish");
    }
}