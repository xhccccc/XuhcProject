package com.xuhc.retrofit_rxjava.together;

import com.xuhc.retrofit_rxjava.retrofit.DataModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET(".")
    Observable<DataModel> get_gson();

}
