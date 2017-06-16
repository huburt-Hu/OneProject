package com.app.julie.oneproject.api;

import com.app.julie.oneproject.bean.ZhihuEntity;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/7.
 */

public interface SurfaceService {

    @GET("api/4/news/latest")
    Flowable<ZhihuEntity> getLatest();

    @GET("api/4/news/before/{date}")
    Flowable<ZhihuEntity> getBefore(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Flowable<ZhihuEntity> getZhihuStory(@Path("id") String id);

}
