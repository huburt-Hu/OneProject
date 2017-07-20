package com.app.julie.oneproject.business.film;

import android.os.Bundle;

import com.app.julie.common.base.BaseActivity;
import com.app.julie.common.base.BaseToolbarActivity;
import com.app.julie.common.util.FragmentUtils;
import com.app.julie.oneproject.R;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/6.
 */

public class FilmActivity extends BaseToolbarActivity {

    private FilmFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        setTitle("电影列表");
        if (fragment == null) {
            fragment = FilmFragment.newInstance();
            FragmentUtils.addFragment(getSupportFragmentManager(), fragment, R.id.container);
        }

        FilmPresenter presenter = new FilmPresenter(fragment);
        fragment.setPresenter(presenter);
    }
}
