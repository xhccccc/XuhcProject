package com.xuhc.retrofit_rxjava.together;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xuhc.retrofit_rxjava.retrofit.DataModel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String TAG = "xhccc" + RetrofitHelper.class.getSimpleName();

    private GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private ApiService apiService;
    private volatile static RetrofitHelper instance = null;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    private RetrofitHelper() {
        init();
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
                .addInterceptor(BasicParamsInterceptor.getDefault());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
            try {
                String text = URLDecoder.decode(message, "utf-8");
                Log.d(TAG,text);
            } catch (UnsupportedEncodingException e) {
                Log.d(TAG,message);
            } catch (Exception e) {
                //出现异常，比如：上传二进制文件
                //不用打印出文件内容了，避免消息太多
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        //设置超时时间
        builder.connectTimeout(6_000, TimeUnit.MILLISECONDS)
                .readTimeout(10_000, TimeUnit.MILLISECONDS)
                .writeTimeout(6_000, TimeUnit.MILLISECONDS);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/wxarticle/chapters/json/")// 设置 网络请求 Url
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 支持RxJava
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Observable<DataModel> get_gson() {
        return apiService.get_gson();
    }
}
