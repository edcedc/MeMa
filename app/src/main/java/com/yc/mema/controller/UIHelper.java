package com.yc.mema.controller;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.yc.mema.MainActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.view.AboutFrg;
import com.yc.mema.view.AccountFrg;
import com.yc.mema.view.AddressFrg;
import com.yc.mema.view.BingPhoneFrg;
import com.yc.mema.view.BlackListFrg;
import com.yc.mema.view.ChangePwdFrg;
import com.yc.mema.view.CollectionFrg;
import com.yc.mema.view.FiveFrg;
import com.yc.mema.view.ForgetFrg;
import com.yc.mema.view.HeadFrg;
import com.yc.mema.view.HelpFrg;
import com.yc.mema.view.InformationFrg;
import com.yc.mema.view.MainFrg;
import com.yc.mema.view.MemaFrg;
import com.yc.mema.view.MsgFrg;
import com.yc.mema.view.PrivacyFrg;
import com.yc.mema.view.SetFrg;
import com.yc.mema.view.SexFrg;
import com.yc.mema.view.UpdateNameFrg;
import com.yc.mema.view.UserInfoFrg;
import com.yc.mema.view.ZkingFrg;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.view.act.LoginAct;
import com.yc.mema.view.act.SetAct;
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
     * 设置
     */
    public static void startSetAct() {
        ActivityUtils.startActivity(SetAct.class);
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
     *  账号与安全
     */
    public static void startAccountFrg(BaseFragment root) {
        AccountFrg frg = new AccountFrg();
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

    /**
     *  么马号
     * @param root
     */
    public static void startMemaFrg(BaseFragment root) {
        MemaFrg frg = new MemaFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  选择性别
     * @param root
     */
    public static void startSexFrg(BaseFragment root) {
        SexFrg frg = new SexFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  设置地址
     * @param root
     */
    public static void startAddressFrg(BaseFragment root) {
        AddressFrg frg = new AddressFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  我的二维码
     * @param root
     */
    public static void startZkingFrg(BaseFragment root) {
        ZkingFrg frg = new ZkingFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  绑定手机号
     * @param root
     */
    public static void startBingPhoneFrg(BaseFragment root) {
        BingPhoneFrg frg = new BingPhoneFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  修改密码
     * @param root
     */
    public static void startChangePwdFrg(BaseFragment root) {
        ChangePwdFrg frg = new ChangePwdFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  消息通知
     * @param root
     */
    public static void startMsgFrg(BaseFragment root) {
        MsgFrg frg = new MsgFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  隐私
     * @param root
     */
    public static void startPrivacyFrg(BaseFragment root) {
        PrivacyFrg frg = new PrivacyFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  黑名单
     * @param root
     */
    public static void startBlackListFrg(BaseFragment root) {
        BlackListFrg frg = new BlackListFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  帮助与反馈
     * @param root
     */
    public static void startHelpFrg(BaseFragment root) {
        HelpFrg frg = new HelpFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  关于我们
     * @param root
     */
    public static void startAboutFrg(BaseFragment root) {
        AboutFrg frg = new AboutFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }
}