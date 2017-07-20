package com.app.julie.oneproject.business.film;

import com.app.julie.common.base.BaseBean;
import com.app.julie.common.base.BaseRvContract;
import com.app.julie.common.mvp.BasePresenterImpl;
import com.app.julie.common.mvp.RespObserver;
import com.app.julie.oneproject.api.ApiManager;
import com.app.julie.oneproject.bean.FilmEntity;

import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/6.
 */

public class FilmPresenter
        extends BasePresenterImpl<BaseRvContract.View<FilmEntity, BaseRvContract.Presenter>>
        implements BaseRvContract.Presenter {


    public FilmPresenter(BaseRvContract.View<FilmEntity, BaseRvContract.Presenter> view) {
        super(view);
    }

    @Override
    public void getData(final int page) {
        ApiManager.getMyWebService()
                .getFilm(page)
                .compose(this.<BaseBean<List<FilmEntity>>>getRequestTransformer())
                .subscribe(new RespObserver<BaseBean<List<FilmEntity>>>() {
                    @Override
                    public void success(BaseBean<List<FilmEntity>> listBaseBean) {
                        getView().onDataReceived(listBaseBean.getData());
                    }

                    @Override
                    public boolean error(Throwable e) {
                        getView().onFailed();
                        return false;
                    }
                });
    }

}
