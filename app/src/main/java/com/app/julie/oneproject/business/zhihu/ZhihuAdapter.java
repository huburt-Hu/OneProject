package com.app.julie.oneproject.business.zhihu;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.app.julie.common.imageloader.ImageLoaderManager;
import com.app.julie.common.imageloader.ImageLoaderOptions;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.bean.ZhihuEntity;
import com.app.julie.oneproject.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/5.
 */

public class ZhihuAdapter extends BaseQuickAdapter<ZhihuEntity.Stories, BaseViewHolder> {

    public ZhihuAdapter(@Nullable List<ZhihuEntity.Stories> data) {
        super(R.layout.item_main, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ZhihuEntity.Stories stories) {
        ImageView icon = holder.getView(R.id.iv);
        String image = stories.getImage();
        List<String> images = stories.getImages();
        if (!TextUtils.isEmpty(image)) {
            ImageLoaderManager.getInstance().load(icon, image,
                    new ImageLoaderOptions.Builder().transformations(
                            new GlideRoundTransform(icon.getContext(), 5)).build());
        } else if (images != null && !TextUtils.isEmpty(images.get(0))) {
            ImageLoaderManager.getInstance().load(icon, images.get(0),
                    new ImageLoaderOptions.Builder().transformations(
                            new GlideRoundTransform(icon.getContext(), 5)).build());
//            Glide.with(icon.getContext())
//                    .load(images.get(0))
//                    .bitmapTransform(new GlideRoundTransform(icon.getContext(), 5))
//                    .into(icon);
        }
        String title = stories.getTitle();
        if (!TextUtils.isEmpty(title)) {
            holder.setText(R.id.tv, title);
        }
    }
}
