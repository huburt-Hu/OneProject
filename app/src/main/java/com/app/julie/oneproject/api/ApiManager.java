package com.app.julie.oneproject.api;

import android.support.annotation.NonNull;

import com.app.julie.common.util.SPUtils;
import com.app.julie.oneproject.BuildConfig;
import com.app.julie.oneproject.constant.SpConstant;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/7.
 */

public class ApiManager {

    public static final String surfaceBaseUrl = "http://news-at.zhihu.com/";
    public static final String meiziBaseUrl = "http://gank.io/";
    public static final String BASE_URL_OFFICE = "http://192.168.31.243:8080/androidWeb/";
//    public static final String baseUrl = "http://192.168.0.102:8080/androidWeb/";
    //5507d1ac.all123.net

    public static String baseUrl = BASE_URL_OFFICE;

    public static ZhihuService getZhihuService() {
        Retrofit.Builder builder = getRetrofitBuilder();
        builder.baseUrl(surfaceBaseUrl);
        return builder.build().create(ZhihuService.class);
    }

    public static MeiziService getMeiziService() {
        Retrofit.Builder builder = getRetrofitBuilder();
        builder.baseUrl(meiziBaseUrl);
        return builder.build().create(MeiziService.class);
    }

    @NonNull
    private static Retrofit.Builder getRetrofitBuilder() {
        OkHttpClient httpClient = new OkHttpClient();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        }
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient);
    }

    public static MyWebService getMyWebService() {
        String url = new SPUtils(SpConstant.SP_CONFIG).getString(SpConstant.BASE_URL);
        if (url != null) {
            baseUrl = url;
        }
        Retrofit.Builder builder = getRetrofitBuilder();
        builder.baseUrl(baseUrl);
        return builder.build().create(MyWebService.class);
    }
}
