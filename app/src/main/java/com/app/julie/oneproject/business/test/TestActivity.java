package com.app.julie.oneproject.business.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.julie.common.util.ToastUtils;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.widget.DateTimePickerDialog;
import com.app.julie.oneproject.widget.SingleChooseDialog;

import java.util.Calendar;

public class TestActivity extends AppCompatActivity {

    private DateTimePickerDialog timePickerDialog;
    private boolean aBoolean;
    private SingleChooseDialog chooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePickerDialog == null) {
                    timePickerDialog = new DateTimePickerDialog(TestActivity.this);
                    timePickerDialog.setOnDateSelectedListener(new DateTimePickerDialog.OnDateSelectedListener() {
                        @Override
                        public void onDateSelected(Calendar calendar) {
                            ToastUtils.showShortToast(calendar.toString());
                        }
                    });
                }
                if (aBoolean) {
                    timePickerDialog.show(Calendar.getInstance(), false);
                } else {
                    timePickerDialog.show(true);
                }
                aBoolean = !aBoolean;
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseDialog == null) {
                    chooseDialog = new SingleChooseDialog(TestActivity.this, null);
                    chooseDialog.setOnClickListener(new SingleChooseDialog.OnClickListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onOkClick(String choose) {
                            ToastUtils.showShortToast(choose);
                        }
                    });
                }
                chooseDialog.show();
            }
        });
    }
}
