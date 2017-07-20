package com.app.julie.oneproject.business.set;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.julie.common.base.BaseFragment;
import com.app.julie.common.util.SPUtils;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.api.ApiManager;
import com.app.julie.oneproject.constant.SpConstant;

import java.util.Locale;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/11.
 */

public class SetFragment extends BaseFragment {

    private SPUtils spUtils;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set;
    }

    public static SetFragment getInstance() {
        return new SetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = new SPUtils(SpConstant.SP_CONFIG);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = (Button) view.findViewById(R.id.btn);
        final EditText editText = (EditText) view.findViewById(R.id.et);
        editText.setText(ApiManager.baseUrl);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spUtils.put(SpConstant.BASE_URL, editText.getText().toString());
                getActivity().finish();
            }
        });
    }
}
