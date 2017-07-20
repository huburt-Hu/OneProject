package com.app.julie.oneproject.business.meizi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.app.julie.common.base.BaseRvContract;
import com.app.julie.common.base.BaseRvFragment;
import com.app.julie.common.imageloader.ImageLoaderManager;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.bean.MeiziEntity;
import com.app.julie.oneproject.business.photo.PhotoActivity;
import com.app.julie.oneproject.util.TransitionHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/7.
 */

public class MeiziFragment extends BaseRvFragment<MeiziEntity.ResultsBean, BaseRvContract.Presenter> {

    @Override
    public int[] getRefreshLayoutColor() {
        return new int[]{getResources().getColor(R.color.colorAccent)};
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MeiziEntity.ResultsBean bean = mAdapter.getData().get(position);
//                PhotoActivity.start(getActivity(), bean.getUrl());
                View target = view.findViewById(R.id.iv);
                Pair<View, String>[] pairs =
                        TransitionHelper.createSafeTransitionParticipants(getActivity(), false,
                                new Pair<>(target, "target"));
                Intent i = new Intent(getActivity(), PhotoActivity.class);
                i.putExtra(PhotoActivity.IMAGE_URL, bean.getUrl());
                ActivityOptionsCompat transitionActivityOptions =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
                getActivity().startActivity(i, transitionActivityOptions.toBundle());
            }
        });
    }

    @Override
    public BaseQuickAdapter<MeiziEntity.ResultsBean, BaseViewHolder> onCreateAdapter(List<MeiziEntity.ResultsBean> list) {
        return new BaseQuickAdapter<MeiziEntity.ResultsBean, BaseViewHolder>(R.layout.view_meizi, list) {
            @Override
            protected void convert(BaseViewHolder helper, MeiziEntity.ResultsBean item) {
                ImageView iv = helper.getView(R.id.iv);
                ImageLoaderManager.getInstance().load(iv, item.getUrl());
            }
        };
    }

    public static MeiziFragment newInstance() {
        return new MeiziFragment();
    }

}
