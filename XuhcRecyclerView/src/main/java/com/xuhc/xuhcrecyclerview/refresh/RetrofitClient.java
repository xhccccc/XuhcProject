package com.xuhc.xuhcrecyclerview.refresh;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xuhc on 2021/12/17
 */

public class RetrofitClient {

    public RefreshService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(RefreshService.class);
    }
}
