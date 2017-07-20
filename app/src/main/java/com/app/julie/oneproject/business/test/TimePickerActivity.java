package com.app.julie.oneproject.business.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.julie.common.util.LogUtils;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.widget.TimeSectionPicker;

import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn1;
    private Button btn2;
    private Button btn3;
    private TimeSectionPicker timeSectionPicker;
    private Button btn4;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        timeSectionPicker = (TimeSectionPicker) findViewById(R.id.time_picker);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int start;
        int end;
        switch (v.getId()) {
            case R.id.btn1:
                start = timeSectionPicker.getTimeNumber(9, 0);
                end = timeSectionPicker.getTimeNumber(18, 0);
                timeSectionPicker.setBookArea(start, end - start);
                break;
            case R.id.btn2:
                start = timeSectionPicker.getTimeNumber(9, 0);
                end = timeSectionPicker.getTimeNumber(12, 0);
                timeSectionPicker.setBookArea(start, end - start);
                break;
            case R.id.btn3:
                start = timeSectionPicker.getTimeNumber(13, 0);
                end = timeSectionPicker.getTimeNumber(18, 0);
                timeSectionPicker.setBookArea(start, end - start);

                break;
            case R.id.btn4:
                start = new Random().nextInt(14) + 1;
                end = new Random().nextInt(3) + 1;
                LogUtils.e("start:" + start);
                LogUtils.e("end:" + end);
                timeSectionPicker.addUsed(new int[]{start, end});
                break;
        }
    }

}
