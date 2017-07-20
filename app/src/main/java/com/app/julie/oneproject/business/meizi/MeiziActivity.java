package com.app.julie.oneproject.business.meizi;

import android.os.Bundle;

import com.app.julie.common.base.BaseToolbarActivity;
import com.app.julie.common.util.FragmentUtils;
import com.app.julie.oneproject.R;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/7.
 */

public class MeiziActivity extends BaseToolbarActivity {


    private MeiziFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        setTitle("妹纸");
        if (fragment == null) {
            fragment = MeiziFragment.newInstance();
            FragmentUtils.addFragment(getSupportFragmentManager(), fragment, R.id.container);
        }

        MeiziPresenter presenter = new MeiziPresenter(fragment);
        fragment.setPresenter(presenter);
    }
}
