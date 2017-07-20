package com.app.julie.oneproject.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.julie.common.base.BaseActivity;
import com.app.julie.common.mvp.SchedulersTransformer;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.business.main.BottomNavigationActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/10.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(3, TimeUnit.SECONDS)
                .compose(SchedulersTransformer.<Long>io_ui())
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        startActivity(new Intent(SplashActivity.this, BottomNavigationActivity.class));
                        finish();
                    }
                });
    }
}
