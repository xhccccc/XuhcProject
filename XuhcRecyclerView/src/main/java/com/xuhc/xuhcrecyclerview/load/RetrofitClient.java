package com.xuhc.xuhcrecyclerview.load;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xuhc on 2021/12/17
 *
 * !!!api失效，看使用方法即可!!!
 */

public class RetrofitClient {

    public LoadService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(LoadService.class);
    }
}
