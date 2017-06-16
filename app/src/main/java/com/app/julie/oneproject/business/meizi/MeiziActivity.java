package com.app.julie.oneproject.business.meizi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.julie.common.util.FragmentUtils;
import com.app.julie.oneproject.R;

public class MeiziActivity extends AppCompatActivity {

    private MeiziFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        init();
    }

    private void init() {
        if (fragment == null) {
            fragment = MeiziFragment.newInstance();
            FragmentUtils.addFragment(getSupportFragmentManager(), fragment, R.id.container);
        }
        new MeiziPresenter(fragment);


    }
}
