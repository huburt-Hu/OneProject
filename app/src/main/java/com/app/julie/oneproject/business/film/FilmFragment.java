package com.app.julie.oneproject.business.film;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.app.julie.common.base.BaseRvContract;
import com.app.julie.common.base.BaseRvFragment;
import com.app.julie.common.imageloader.ImageLoaderManager;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.bean.FilmEntity;
import com.app.julie.oneproject.business.palyer.PlayerActivity;
import com.app.julie.oneproject.util.TransitionHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/6.
 */

public class FilmFragment extends BaseRvFragment<FilmEntity, BaseRvContract.Presenter> {


    public static FilmFragment newInstance() {
        return new FilmFragment();
    }

    @Override
    public int[] getRefreshLayoutColor() {
        return new int[]{getResources().getColor(R.color.colorAccent)};
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FilmEntity entity = FilmFragment.this.mAdapter.getData().get(position);
//                PlayerActivity.start(getActivity(), entity.getTitle(), entity.getImage(), entity.getUrl());
                View target = view.findViewById(R.id.videoplayer);
                View tv = view.findViewById(R.id.tv_title);
                Pair<View, String>[] pairs =
                        TransitionHelper.createSafeTransitionParticipants(getActivity(), false,
                                new Pair<>(target, "target"), new Pair<>(tv, "title"));
                Intent i = new Intent(getActivity(), PlayerActivity.class);
                i.putExtra(PlayerActivity.TITLE, entity.getTitle());
                i.putExtra(PlayerActivity.IMAGE, entity.getImage());
                i.putExtra(PlayerActivity.URL, entity.getUrl());
                ActivityOptionsCompat transitionActivityOptions =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
                getActivity().startActivity(i, transitionActivityOptions.toBundle());
            }
        });
    }

    @Override
    public BaseQuickAdapter<FilmEntity, BaseViewHolder> onCreateAdapter(List<FilmEntity> list) {
        return new BaseQuickAdapter<FilmEntity, BaseViewHolder>(R.layout.fragment_film, list) {
            @Override
            protected void convert(BaseViewHolder helper, FilmEntity item) {
                helper.setText(R.id.tv_title, item.getTitle());
                ImageView iv = helper.getView(R.id.videoplayer);
                ImageLoaderManager.getInstance().load(iv, item.getImage());
            }
        };
    }
}
