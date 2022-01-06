package com.xuhc.retrofit_rxjava.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET(".")
    Call<ResponseBody> get();

    @GET(".")
    Call<DataModel> get_gson();

    @GET("api/{path}")
    Call<ResponseBody> get(@Path("path")int path);

    @FormUrlEncoded
    @POST(".")
    Call<ResponseBody> post(@Field("ip")String first);

}
