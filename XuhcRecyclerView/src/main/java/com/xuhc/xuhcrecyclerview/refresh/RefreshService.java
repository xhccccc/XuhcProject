package com.xuhc.xuhcrecyclerview.refresh;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Xuhc on 2021/12/17
 */

public interface RefreshService {

    /**
     * API 示例
     *
     * https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=10&client=&udid=
     */
    @GET("/v2/movie/in_theaters")
    Observable<RefreshDataBean> getRefreshData(@Query("apikey") String apikey,
                                               @Query("city") String city,
                                               @Query("start") int start,
                                               @Query("count") int count,
                                               @Query("client") String client,
                                               @Query("udid") String udid);
}
