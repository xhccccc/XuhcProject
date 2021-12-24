package com.xuhc.xuhcrecyclerview.timeline;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Xuhc on 2021/12/17
 */

public interface TimelineService {

    /**
     * https://www.kuaidi100.com/query?type=zhongtong&postid=482357364071
     */
    @GET("query")
    Observable<TimelineBean> getTimeline(@Query("type") String type,
                                         @Query("postid") String postid);
}
