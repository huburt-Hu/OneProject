package com.app.julie.oneproject.business.main;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.app.julie.common.base.BaseBean;
import com.app.julie.common.mvp.BasePresenterImpl;
import com.app.julie.common.mvp.RespObserver;
import com.app.julie.common.util.SPUtils;
import com.app.julie.oneproject.api.ApiManager;
import com.app.julie.oneproject.bean.UpdateEntity;
import com.app.julie.oneproject.constant.SpConstant;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/10.
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View>
        implements MainContract.Presenter {

    //提示更新间隔
    public static final int INTERVAL = 1000 * 60 * 60 * 24 * 7;

    public MainPresenter(MainContract.View view) {
        super(view);
    }


    @Override
    public void checkVersion() {
        ApiManager.getMyWebService()
                .checkVersion()
                .compose(this.<BaseBean<UpdateEntity>>getRequestTransformer())
                .subscribe(new RespObserver<BaseBean<UpdateEntity>>() {
                    @Override
                    public void success(BaseBean<UpdateEntity> updateEntityBaseBean) {
                        UpdateEntity data = updateEntityBaseBean.getData();
                        if (data.getVersionCode() > getVersionCode()) {
                            long last = new SPUtils(SpConstant.SP_UPDATE).getLong(SpConstant.LAST_ALERT_TIME);
                            long currentTimeMillis = System.currentTimeMillis();
                            if (currentTimeMillis - last > INTERVAL) {
                                getView().showUpdateDialog(data);
                            }
                        }
                    }
                });
    }

    private int getVersionCode() {
        PackageManager packageManager = getContext().getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 1;
        try {
            packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
