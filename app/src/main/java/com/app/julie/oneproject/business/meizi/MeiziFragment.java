package com.app.julie.oneproject.business.meizi;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.julie.common.calculation.linked_list.MyIntentService;
import com.app.julie.common.calculation.sort.FastSort;
import com.app.julie.oneproject.R;

import java.util.Arrays;

/**
 * Created by julie
 * <p>
 * Created on 2017/5/8.
 */

public class MeiziFragment extends MeiziContract.View {

    public static MeiziFragment newInstance() {
        return new MeiziFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meizi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button btn = (Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int[] array = {4, 2, 6, 9, 1, 7, 3};
//                long start = SystemClock.currentThreadTimeMillis();
//                FastSort.quick(array, 0, array.length - 1);
//                long end = SystemClock.currentThreadTimeMillis();
//                Log.i("tag", "usedTime:" + (end - start));
//                Log.i("tag", "array:" + Arrays.toString(array));
                MyIntentService.startActionBaz(getActivity(), "", "");
                MyIntentService.startActionFoo(getActivity(), "", "");
            }
        });
    }
}
