package com.yc.mema.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by yc on 2017/12/29.
 */

public class X5WebView extends WebView {

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);

        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings(arg0);
//        this.getView().setClickable(true);
    }

    private void initWebViewSettings(Context context) {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);// 设置支持javascript脚本
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setDomStorageEnabled(true);//开启本地DOM存储
        webSetting.setAllowFileAccess(true);// 允许访问文件
        /**
         *  用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true); // 支持缩放
        webSetting.setBuiltInZoomControls(false);// 设置显示缩放按钮
        webSetting.setUseWideViewPort(true);//设置webview推荐使用的窗口 //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);//设置webview加载的页面的模式  //设置默认加载的可视范围是大视野范围
        webSetting.setAppCacheEnabled(true); // 设置启动缓存
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setTextZoom(120);
        setLayerType(View.LAYER_TYPE_NONE,null);//开启硬件加速


    }

}
