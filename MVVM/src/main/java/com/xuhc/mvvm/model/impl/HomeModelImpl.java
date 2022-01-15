/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xuhc.mvvm.model.impl;

import android.os.CountDownTimer;
import android.util.Log;

import com.xuhc.mvvm.model.HomeModel;
import com.xuhc.mvvm.viewmodel.OnHomeListener;

import androidx.lifecycle.MutableLiveData;

/**
 * 业务模型层
 *
 * @author venshine
 */
public class HomeModelImpl implements HomeModel {

    private static final String TAG = "xhccc" + HomeModelImpl.class.getSimpleName();

    public HomeModelImpl() {
    }


    @Override
    public void getCountDownNumber(MutableLiveData<Long> liveData, OnHomeListener onHomeListener) {
        new CountDownTimer(1 * 60 * 1000, 1 * 1000) {
            @Override
            public void onTick(long l) {
                Log.i(TAG, "onTick: " + l);
                onHomeListener.onTickSucceed(l);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish");
                onHomeListener.onTickFinish();
            }
        }.start();
    }
}
