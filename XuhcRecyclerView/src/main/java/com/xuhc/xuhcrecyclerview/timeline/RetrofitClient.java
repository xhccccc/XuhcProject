package com.xuhc.xuhcrecyclerview.timeline;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xuhc on 2021/12/17
 */

public class RetrofitClient {

    public TimelineService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(TimelineService.class);
    }
}
