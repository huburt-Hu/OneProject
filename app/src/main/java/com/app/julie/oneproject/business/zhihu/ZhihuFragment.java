package com.app.julie.oneproject.business.zhihu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.app.julie.common.mvp.BaseViewImplFragment;
import com.app.julie.common.util.LogUtils;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.bean.ZhihuEntity;
import com.app.julie.oneproject.business.zhihudetail.ZhihuDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZhihuFragment extends BaseViewImplFragment<ZhihuContract.Presenter>
        implements ZhihuContract.View {


    private RecyclerView recyclerView;
    private ZhihuAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    private String lastDate;

    public static ZhihuFragment newInstance() {
        return new ZhihuFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
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
        List<ZhihuEntity.Stories> stories = new ArrayList<>();
        adapter = new ZhihuAdapter(stories);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ZhihuEntity.Stories data = ZhihuFragment.this.adapter.getData().get(position);
                ZhihuDetailActivity.start(getActivity(), data.getTitle(), data.getId());
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (!TextUtils.isEmpty(lastDate)) {
                    mPresenter.getMoreData(lastDate);
                } else {
                    mPresenter.getData();
                }
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    public void onDataReceived(ZhihuEntity entity, boolean isRefresh) {
        refreshLayout.setRefreshing(false);
        adapter.loadMoreComplete();
        lastDate = entity.getDate();
        List<ZhihuEntity.Stories> data = new ArrayList<>();
        List<ZhihuEntity.Stories> stories = entity.getStories();
        if (stories != null) {
            data.addAll(stories);
        }

        LogUtils.e("onDataReceived:data/" + data.size());
        if (isRefresh) {
            adapter.setNewData(data);
        } else {
            if (data.size() > 0) {
                adapter.addData(data);
            } else {
                adapter.loadMoreEnd(true);
                adapter.setEnableLoadMore(false);
            }
        }
    }
}
