package com.app.julie.oneproject.business.main;

import com.app.julie.oneproject.api.ApiManager;
import com.app.julie.oneproject.api.SurfaceService;
import com.app.julie.oneproject.bean.ZhihuEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/5.
 */

public class MainPresenter extends MainContract.Presenter {

    private CompositeDisposable compositeDisposable;
    private SurfaceService surfaceService;

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        compositeDisposable = new CompositeDisposable();
        surfaceService = ApiManager.getSurfaceService();
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void getData() {

        Flowable<ZhihuEntity> flowable = surfaceService.getLatest();
        compositeDisposable.add(flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZhihuEntity>() {
                    @Override
                    public void accept(@NonNull ZhihuEntity zhihuEntity) throws Exception {
                        if (isViewActive()) {
                            getView().onDataReceived(zhihuEntity,true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (isViewActive()) {
                            getView().showToast("网络异常!");
                        }
                    }
                }));
    }

    @Override
    public void getMoreData(String lastDate) {
        compositeDisposable.add(surfaceService.getBefore(lastDate)
                .delay(1, TimeUnit.SECONDS)//模拟延迟，注意要在observeOn之前调用
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZhihuEntity>() {
                    @Override
                    public void accept(@NonNull ZhihuEntity entity) throws Exception {
                        if (isViewActive()) {
                            getView().onDataReceived(entity, false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (isViewActive()) {
                            getView().showToast("网络异常!");
                        }
                    }
                }));
    }
}
