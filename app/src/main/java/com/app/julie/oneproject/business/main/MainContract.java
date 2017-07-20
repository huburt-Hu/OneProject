package com.app.julie.oneproject.business.main;

import com.app.julie.common.mvp.BasePresenter;
import com.app.julie.common.mvp.BaseView;
import com.app.julie.oneproject.bean.UpdateEntity;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/10.
 */

public interface MainContract {
    interface Presenter extends BasePresenter {
        void checkVersion();
    }

    interface View extends BaseView<Presenter> {
        void showUpdateDialog(UpdateEntity updateEntity);
    }

}
