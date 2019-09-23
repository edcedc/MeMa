package com.yc.mema.utils;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yc.mema.R;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        DataBean bean = (DataBean) path;
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.no_banner);
        Glide.with(context.getApplicationContext())
                .load(CloudApi.SERVLET_IMG_URL + (bean.getImageId() == null ? bean.getAttachId() : bean.getImageId()))
                .apply(options)
                .into(imageView);
    }

//    @Override
//    public ImageView createImageView(Context context) {
//        //圆角
//        return new RoundAngleImageView(context);
//    }
}
