package com.app.julie.oneproject.api;

import com.app.julie.common.base.BaseBean;
import com.app.julie.oneproject.bean.FilmEntity;
import com.app.julie.oneproject.bean.UpdateEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/6.
 */

public interface MyWebService {

    @GET("update")
    Observable<BaseBean<UpdateEntity>> checkVersion();

    @GET("film/all")
    Observable<BaseBean<List<FilmEntity>>> getFilm(@Query("page") int page);
}
