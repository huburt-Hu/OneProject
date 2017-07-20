package com.app.julie.oneproject.api;

import com.app.julie.oneproject.bean.ZhihuDetailEntity;
import com.app.julie.oneproject.bean.ZhihuEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/7.
 */

public interface ZhihuService {

    @GET("api/4/news/latest")
    Observable<ZhihuEntity> getLatest();

    @GET("api/4/news/before/{date}")
    Observable<ZhihuEntity> getBefore(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhihuDetailEntity> getZhihuStory(@Path("id") String id);

}
