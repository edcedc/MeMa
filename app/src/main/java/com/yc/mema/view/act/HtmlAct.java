package com.yc.mema.view.act;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.databinding.AHtmlBinding;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  统一H5
 */

public class HtmlAct extends BaseActivity<BasePresenter, AHtmlBinding> {

    private int type = -1;
    private String url;

    public static final int SYSTEM = 1;//系统通知
    public static final int REGISTER = 4;//隐私协议
    public static final int TENTRY = 5;//入驻类型说明


    public static final int LOOK_WULIU = 2;//查看物流
    public static final int ABOUT = 99;
    private String title;


    @Override
    public void initPresenter() {mPresenter.init(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.a_html;
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
        url = bundle.getString("url");
        title = bundle.getString("title");
    }

    @Override
    protected void initView() {
        setSofia(true);
        switch (type){
            case SYSTEM:
                setTitle("系统通知");
                mB.webView.loadUrl(url);
                break;
            case REGISTER:
                setTitle("隐私协议");
                mB.webView.loadUrl(url);
                break;
            case LOOK_WULIU:
                setTitle("物流信息");
                mB.webView.loadUrl(url);
                break;
            case TENTRY:
                setTitle("入驻类型说明");
                mB.webView.loadUrl(url);
                break;
            default:
                setTitle(title);
                mB.webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
                break;
        }
        mB.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                LogUtils.e(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                mB.progressBar.setVisibility(View.GONE);
                ToastUtils.showShort("网页加载失败");
            }
        });
        //进度条
        mB.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mB.progressBar.setVisibility(View.GONE);
                    return;
                }
                mB.progressBar.setVisibility(View.VISIBLE);
                mB.progressBar.setProgress(newProgress);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mB.webView.canGoBack()) {
            mB.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mB.webView.removeAllViews();
        mB.webView.destroy();
        super.onDestroy();
    }
}
