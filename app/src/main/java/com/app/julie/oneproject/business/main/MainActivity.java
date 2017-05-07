package com.app.julie.oneproject.business.main;

import android.os.Bundle;

import com.app.julie.common.base.BaseActivity;
import com.app.julie.common.util.FragmentUtils;
import com.app.julie.oneproject.R;


public class MainActivity extends BaseActivity {

    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (fragment == null) {
            fragment = MainFragment.newInstance();
            FragmentUtils.addFragment(getSupportFragmentManager(), fragment, R.id.container);
        }

        new MainPresenter(fragment);
    }
}
