package com.yc.mema.controller;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.yc.mema.MainActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.view.CollectionFrg;
import com.yc.mema.view.FiveFrg;
import com.yc.mema.view.ForgetFrg;
import com.yc.mema.view.HeadFrg;
import com.yc.mema.view.InformationFrg;
import com.yc.mema.view.MainFrg;
import com.yc.mema.view.UpdateNameFrg;
import com.yc.mema.view.UserInfoFrg;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.view.act.LoginAct;
import com.yc.mema.view.act.UserInfoAct;


/**
 * Created by Administrator on 2017/2/22.
 */

public final class UIHelper {

    private UIHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void startMainAct() {
        ActivityUtils.startActivity(MainActivity.class);
    }

    /**
     *  登录
     */
    public static void startLoginAct() {
        ActivityUtils.startActivity(LoginAct.class);
    }

    /**
     *  找回密码
     * @param root
     */
    public static void startForgetFrg(BaseFragment root) {
        ForgetFrg frg = new ForgetFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  完善信息
     * @param root
     */
    public static void startInformationFrg(BaseFragment root) {
        InformationFrg frg = new InformationFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * html
     */
    public static void startHtmlAct(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        ActivityUtils.startActivity(bundle, HtmlAct.class);
    }

    /**
     * 个人信息
     */
    public static void startUserInfoAct() {
        ActivityUtils.startActivity(UserInfoAct.class);
    }

    /**
     *  收藏
     */
    public static void startCollectionFrg(BaseFragment root) {
        CollectionFrg frg = new CollectionFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  个人信息
     * @param root
     */
    public static void startUserInfoFrg(BaseFragment root) {
        UserInfoFrg frg = new UserInfoFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  修改头像
     */
    public static void startHeadFrg(BaseFragment root) {
        HeadFrg frg = new HeadFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  修改名字
     * @param root
     */
    public static void startUpdateNameFrg(BaseFragment root) {
        UpdateNameFrg frg = new UpdateNameFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

}