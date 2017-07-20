package com.app.julie.oneproject.business.zhihu;

import com.app.julie.common.mvp.BasePresenter;
import com.app.julie.common.mvp.BaseView;
import com.app.julie.oneproject.bean.ZhihuEntity;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/5.
 */

public interface ZhihuContract {

    interface View extends BaseView<Presenter> {
        void onDataReceived(ZhihuEntity entity, boolean isRefresh);
    }

    interface Presenter extends BasePresenter {
        void getData();

        void getMoreData(String lastDate);
    }

}
