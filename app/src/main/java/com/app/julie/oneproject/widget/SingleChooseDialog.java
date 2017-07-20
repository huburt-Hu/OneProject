package com.app.julie.oneproject.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.app.julie.oneproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/18.
 */

public class SingleChooseDialog extends UpDialog implements View.OnClickListener {

    private TextView tvText;
    private OnClickListener onClickListener;
    private List<String> data;

    public SingleChooseDialog(@NonNull Context context, List<String> data) {
        super(context);
        this.data = data;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_single_choose;
    }

    @Override
    protected void onViewCreated(View view) {
        tvText = (TextView) view.findViewById(R.id.tv_text);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        WheelPicker wheelPicker = (WheelPicker) view.findViewById(R.id.wheel_picker);

        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        if (data == null) {
            data = new ArrayList<>();
            data.add("缺少选项");

            data.add("123");
            data.add("abc");
            data.add("xyz");
            data.add("qwerf");
            data.add("adva");
        }
        tvText.setText(data.get(0));
        wheelPicker.setData(data);
        wheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                tvText.setText(String.valueOf(data));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                if (onClickListener != null)
                    onClickListener.onCancelClick();
                dismiss();
                break;
            case R.id.tv_ok:
                if (onClickListener != null)
                    onClickListener.onOkClick(tvText.getText().toString().trim());
                dismiss();
                break;
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static interface OnClickListener {
        void onCancelClick();

        void onOkClick(String choose);
    }
}

