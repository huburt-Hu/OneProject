package com.app.julie.oneproject.business.zhihu;

import com.app.julie.common.mvp.BasePresenterImpl;
import com.app.julie.common.mvp.RespObserver;
import com.app.julie.oneproject.api.ApiManager;
import com.app.julie.oneproject.api.ZhihuService;
import com.app.julie.oneproject.bean.ZhihuEntity;

import java.util.concurrent.TimeUnit;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/5.
 */

public class ZhihuPresenter extends BasePresenterImpl<ZhihuContract.View>
        implements ZhihuContract.Presenter {

    private ZhihuService surfaceService;

    public ZhihuPresenter(ZhihuContract.View view) {
        super(view);
    }

    @Override
    public void subscribe() {
        surfaceService = ApiManager.getZhihuService();
    }

    @Override
    public void getData() {
        surfaceService.getLatest()
                .compose(this.<ZhihuEntity>getRequestTransformer())
                .subscribe(new RespObserver<ZhihuEntity>() {
                    @Override
                    public void success(ZhihuEntity entity) {
                        if (isViewActive()) {
                            getView().onDataReceived(entity, true);
                        }
                    }
                });
    }

    @Override
    public void getMoreData(String lastDate) {
        surfaceService.getBefore(lastDate)
                .delay(1, TimeUnit.SECONDS)//模拟延迟，注意要在observeOn之前调用
                .compose(this.<ZhihuEntity>getRequestTransformer())
                .subscribe(new RespObserver<ZhihuEntity>() {
                    @Override
                    public void success(ZhihuEntity entity) {
                        if (isViewActive()) {
                            getView().onDataReceived(entity, false);
                        }
                    }
                });
    }
}
