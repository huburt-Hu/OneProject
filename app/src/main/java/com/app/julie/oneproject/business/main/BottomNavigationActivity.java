package com.app.julie.oneproject.business.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.app.julie.common.base.BaseActivity;
import com.app.julie.common.download.UpdateApkService;
import com.app.julie.common.mvp.BaseViewImplActivity;
import com.app.julie.common.util.FragmentUtils;
import com.app.julie.common.util.SPUtils;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.bean.UpdateEntity;
import com.app.julie.oneproject.business.film.FilmFragment;
import com.app.julie.oneproject.business.film.FilmPresenter;
import com.app.julie.oneproject.business.meizi.MeiziFragment;
import com.app.julie.oneproject.business.meizi.MeiziPresenter;
import com.app.julie.oneproject.business.set.SetFragment;
import com.app.julie.oneproject.business.zhihu.ZhihuFragment;
import com.app.julie.oneproject.business.zhihu.ZhihuPresenter;
import com.app.julie.oneproject.constant.SpConstant;

import java.io.File;

public class BottomNavigationActivity extends BaseViewImplActivity<MainContract.Presenter>
        implements MainContract.View {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectZhihu();
                    return true;
                case R.id.navigation_dashboard:
                    if (meiziFragment == null) {
                        meiziFragment = MeiziFragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, meiziFragment).commit();
                    MeiziPresenter meiziPresenter = new MeiziPresenter(meiziFragment);
                    meiziFragment.setPresenter(meiziPresenter);
                    return true;
                case R.id.navigation_notifications:
                    if (filmFragment == null) {
                        filmFragment = FilmFragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, filmFragment).commit();
                    FilmPresenter filmPresenter = new FilmPresenter(filmFragment);
                    filmFragment.setPresenter(filmPresenter);
                    return true;
                case R.id.set:
                    SetFragment setFragment = SetFragment.getInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, setFragment).commit();
                    return true;
            }
            return false;
        }

    };

    private void selectZhihu() {
        if (zhihuFragment == null) {
            zhihuFragment = ZhihuFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, zhihuFragment).commit();
        ZhihuPresenter zhihuPresenter = new ZhihuPresenter(zhihuFragment);
        zhihuFragment.setPresenter(zhihuPresenter);
    }

    private ZhihuFragment zhihuFragment;
    private MeiziFragment meiziFragment;
    private FilmFragment filmFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        setPresenter(new MainPresenter(this));
        mPresenter.checkVersion();
    }


    @Override
    public void showUpdateDialog(final UpdateEntity updateEntity) {
        new AlertDialog.Builder(this)
                .setTitle("检测更新")
                .setMessage(updateEntity.getMessage())
                .setCancelable(updateEntity.isCancelable())
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOWNLOADS), "aikan.apk");
                        UpdateApkService.startDownload(BottomNavigationActivity.this, R.mipmap.icon_launcher
                                , updateEntity.getBaseUrl(), updateEntity.getUrl(), file);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!updateEntity.isCancelable()) {
                            finish();
                        } else {
                            new SPUtils(SpConstant.SP_UPDATE).put(SpConstant.LAST_ALERT_TIME, System.currentTimeMillis());
                        }
                    }
                }).show();
    }
}
