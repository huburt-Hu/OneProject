package com.app.julie.oneproject.business.main;

import com.app.julie.common.mvp.BasePresenterImpl;
import com.app.julie.common.mvp.BaseViewImpl;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/5.
 */

public interface MainContract {

    abstract class View extends BaseViewImpl<Presenter> {

    }

    abstract class Presenter extends BasePresenterImpl<View> {

        public Presenter(View view) {
            super(view);
        }
    }

}
