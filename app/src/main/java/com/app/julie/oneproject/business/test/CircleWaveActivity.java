package com.app.julie.oneproject.business.test;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;

import com.app.julie.common.base.BaseActivity;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.widget.WaveView;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/7.
 */

public class CircleWaveActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlewave);
//        CircleWaveView circleWaveView = (CircleWaveView) findViewById(R.id.cwv);
//        circleWaveView.startAnimation(1000);

        WaveView mWaveView = (WaveView) findViewById(R.id.wave_view);
        mWaveView.setStyle(Paint.Style.FILL);
        mWaveView.setColor(Color.parseColor("#ff0000"));
        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
        mWaveView.start();
    }
}
