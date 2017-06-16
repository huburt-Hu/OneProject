package com.app.julie.oneproject.api;

import com.app.julie.oneproject.bean.ZhihuEntity;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/8.
 */

public interface MeiziService {

    @GET("api/data/福利/10/{page}")
    Flowable<ZhihuEntity> getMeiziPic(@Path("page") int page);
}
