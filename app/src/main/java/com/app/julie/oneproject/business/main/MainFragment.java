package com.app.julie.oneproject.business.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.julie.common.imageloader.ImageLoader;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.bean.ZhihuEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends MainContract.View {


    private RecyclerView recyclerView;
    private List<ZhihuEntity.Stories> stories;
    private CommonAdapter<ZhihuEntity.Stories> adapter;
    private SwipeRefreshLayout refreshLayout;

    private String lastDate;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        init();
    }

    private void init() {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stories = new ArrayList<>();
        adapter = new CommonAdapter<ZhihuEntity.Stories>(getActivity(), R.layout.item_main, stories) {
            @Override
            protected void convert(ViewHolder holder, ZhihuEntity.Stories stories, int position) {
                ImageView icon = holder.getView(R.id.iv);
                String image = stories.getImage();
                List<String> images = stories.getImages();
                if (!TextUtils.isEmpty(image)) {
                    ImageLoader.getInstance().loadImage(getActivity(), image, icon);
                } else if (images != null && !TextUtils.isEmpty(images.get(0))) {
                    ImageLoader.getInstance().loadImage(getActivity(), images.get(0), icon);
                }
                String title = stories.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    holder.setText(R.id.tv, title);
                }
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                showToast(position + "");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isSlideToBottom(recyclerView)) {
                        refreshLayout.setRefreshing(true);
                        if (!TextUtils.isEmpty(lastDate)) {
                            mPresenter.getMoreData(lastDate);
                        } else {
                            mPresenter.getData();
                        }
                    }
                }
            }
        });
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    public void onDataReceived(ZhihuEntity entity, boolean isRefresh) {
        refreshLayout.setRefreshing(false);
        if (isRefresh) {
            stories.clear();
        }
        List<ZhihuEntity.Stories> stories = entity.getStories();
        if (stories != null) {
            this.stories.addAll(stories);
        }

        List<ZhihuEntity.Stories> top_stories = entity.getTop_stories();
        if (top_stories != null) {
            this.stories.addAll(top_stories);
        }
        adapter.notifyDataSetChanged();
        lastDate = entity.getDate();
    }
}
