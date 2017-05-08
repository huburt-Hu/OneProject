package com.app.julie.oneproject.business.main;

import com.app.julie.common.mvp.BasePresenterImpl;
import com.app.julie.common.mvp.BaseViewImpl;
import com.app.julie.oneproject.bean.ZhihuEntity;

import io.reactivex.Flowable;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/5.
 */

public interface MainContract {

    abstract class View extends BaseViewImpl<Presenter> {
        public abstract void onDataReceived(ZhihuEntity entity, boolean isRefresh);

    }

    abstract class Presenter extends BasePresenterImpl<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void getData();

        public abstract void getMoreData(String lastDate);
    }

}
