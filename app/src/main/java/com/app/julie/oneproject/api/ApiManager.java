package com.app.julie.oneproject.api;

import android.support.annotation.NonNull;

import com.app.julie.oneproject.BuildConfig;
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

    private static final String surfaceBaseUrl = "http://news-at.zhihu.com/";
    private static final String meiziBaseUrl = "http://gank.io/";


    public static SurfaceService getSurfaceService() {
        Retrofit.Builder builder = getRetrofitBuilder();
        builder.baseUrl(surfaceBaseUrl);
        return builder.build().create(SurfaceService.class);
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
}
