package com.xuhc.xuhcrecyclerview.waterfall;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Xuhc on 2021/12/17
 */

public interface WaterfallService {

    /**
     * 福利
     *
     * e.g. http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
     */
    @GET("category/Girl/type/{type}/page/{page}/count/{count}")
    Observable<WaterfallBean> getWaterfallData(@Path("type") String type,@Path("page") int page,@Path("count") int count);
}
