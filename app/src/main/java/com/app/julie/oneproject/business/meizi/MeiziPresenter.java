package com.app.julie.oneproject.business.meizi;

import com.app.julie.common.base.BaseRvContract;
import com.app.julie.common.mvp.BasePresenterImpl;
import com.app.julie.common.mvp.RespObserver;
import com.app.julie.oneproject.api.ApiManager;
import com.app.julie.oneproject.bean.MeiziEntity;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/7.
 */

public class MeiziPresenter
        extends BasePresenterImpl<BaseRvContract.View<MeiziEntity.ResultsBean, BaseRvContract.Presenter>>
        implements BaseRvContract.Presenter {

    public MeiziPresenter(BaseRvContract.View<MeiziEntity.ResultsBean, BaseRvContract.Presenter> view) {
        super(view);
    }

    @Override
    public void getData(int page) {
        ApiManager.getMeiziService().getMeiziPic(page + 1)
                .compose(this.<MeiziEntity>getRequestTransformer())
                .subscribe(new RespObserver<MeiziEntity>() {
                    @Override
                    public void success(MeiziEntity meiziEntity) {
                        getView().onDataReceived(meiziEntity.getResults());
                    }
                });
    }
}
