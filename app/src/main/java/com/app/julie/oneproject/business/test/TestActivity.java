package com.app.julie.oneproject.business.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.julie.oneproject.R;
import com.app.julie.oneproject.widget.TimePicker;

import java.util.Random;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private TimePicker time_picker;
    private Button btn4;

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
        time_picker = (TimePicker) findViewById(R.id.time_picker);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int start;
        int end;
        switch (v.getId()) {
            case R.id.btn1:
                start = time_picker.getTimeInt("09:00");
                end = time_picker.getTimeInt("18:00");
                time_picker.setBookArea(start, end - start);
                break;
            case R.id.btn2:
                start = time_picker.getTimeInt("09:00");
                end = time_picker.getTimeInt("12:00");
                time_picker.setBookArea(start, end - start);
                break;
            case R.id.btn3:
                start = time_picker.getTimeInt("13:00");
                end = time_picker.getTimeInt("18:00");
                time_picker.setBookArea(start, end - start);
                break;
            case R.id.btn4:
                time_picker.addUsed(new int[]{time_picker.getBookStart(), time_picker.getBookCount()});
                break;
        }
    }
}
