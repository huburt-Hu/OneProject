package com.app.julie.oneproject.business.zhihu;

import android.os.Bundle;

import com.app.julie.common.base.BaseToolbarActivity;
import com.app.julie.common.util.FragmentUtils;
import com.app.julie.oneproject.R;


public class ZhihuActivity extends BaseToolbarActivity {

    private ZhihuFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        setTitle("知乎精选");
        if (fragment == null) {
            fragment = ZhihuFragment.newInstance();
            FragmentUtils.addFragment(getSupportFragmentManager(), fragment, R.id.container);
        }

        ZhihuPresenter presenter = new ZhihuPresenter(fragment);
        fragment.setPresenter(presenter);
    }
}
