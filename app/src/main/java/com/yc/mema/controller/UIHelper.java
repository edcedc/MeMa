package com.yc.mema.controller;

import android.app.Activity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.yc.mema.MainActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.view.AboutFrg;
import com.yc.mema.view.AccountFrg;
import com.yc.mema.view.AddBirthdayRecordsFrg;
import com.yc.mema.view.AddressFrg;
import com.yc.mema.view.BingPhoneFrg;
import com.yc.mema.view.BirthdayRecordsFrg;
import com.yc.mema.view.BlackListFrg;
import com.yc.mema.view.ChangePwdFrg;
import com.yc.mema.view.CollectionFrg;
import com.yc.mema.view.ComplaintFrg;
import com.yc.mema.view.ForgetFrg;
import com.yc.mema.view.HeadFrg;
import com.yc.mema.view.HelpFrg;
import com.yc.mema.view.InformationFrg;
import com.yc.mema.view.MainFrg;
import com.yc.mema.view.MemaFrg;
import com.yc.mema.view.MemorandumFrg;
import com.yc.mema.view.MessageFrg;
import com.yc.mema.view.MsgFrg;
import com.yc.mema.view.PrivacyFrg;
import com.yc.mema.view.ProneFrg;
import com.yc.mema.view.ReportFrg;
import com.yc.mema.view.ReportNewsFrg;
import com.yc.mema.view.SearchGiftFrg;
import com.yc.mema.view.SearchNewsFrg;
import com.yc.mema.view.SexFrg;
import com.yc.mema.view.SystemDescFrg;
import com.yc.mema.view.UpdateNameFrg;
import com.yc.mema.view.UserInfoFrg;
import com.yc.mema.view.ZkingFrg;
import com.yc.mema.view.act.GiftAct;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.view.act.LoginAct;
import com.yc.mema.view.act.NewsDescAct;
import com.yc.mema.view.act.ReleaseAct;
import com.yc.mema.view.act.VideoAct;
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
     *  y24
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
     *  系统通知详情
     */
    public static void startSystemDescFrg(BaseFragment root, DataBean bean) {
        SystemDescFrg frg = new SystemDescFrg();
        Bundle bundle = new Bundle();
        bundle.putString("bean", new Gson().toJson(bean));
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
    public static void startAddressFrg(BaseFragment root, int type) {
        AddressFrg frg = new AddressFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        if (type == 0){
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }else {
            root.start(frg);
        }
    }
    public static void startAddressFrg(BaseFragment root, int type, boolean isUpdate) {
        AddressFrg frg = new AddressFrg();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isUpdate", isUpdate);
        frg.setArguments(bundle);
        if (type == 0){
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }else {
            root.start(frg);
        }
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
     *  记事本
     * @param root
     */
    public static void startMemorandumFrgFrg(BaseFragment root) {
        MemorandumFrg frg = new MemorandumFrg();
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

    /**
     *  生日备忘录
     * @param root
     */
    public static void startBirthdayRecordsFrg(BaseFragment root) {
        BirthdayRecordsFrg frg = new BirthdayRecordsFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  添加生日记录
     * @param root
     */
    public static void startAddBirthdayRecordsFrg(BaseFragment root) {
        AddBirthdayRecordsFrg frg = new AddBirthdayRecordsFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  用户投诉反馈
     * @param root
     * @param id
     */
    public static void startComplaintFrg(BaseFragment root, String id, int type) {
        ComplaintFrg frg = new ComplaintFrg();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("type", type);
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  投诉
     */
    public static void startReportFrg(BaseFragment root, String id, int type, String soId, String soName) {
        ReportFrg frg = new ReportFrg();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("soId", soId);
        bundle.putString("soName", soName);
        bundle.putInt("type", type);
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  添加生日记录
     * @param welId
     */
    public static void startGiftAct(String welId) {
        Bundle bundle = new Bundle();
        bundle.putString("id", welId);
        ActivityUtils.startActivity(bundle, GiftAct.class);
    }

    /**
     *  搜索礼包
     * @param root
     * @param parentId
     * @param s
     */
    public static void startSearchGiftFrg(BaseFragment root, String parentId, String s) {
        SearchGiftFrg frg = new SearchGiftFrg();
        Bundle bundle = new Bundle();
        bundle.putString("parentId", parentId);
        bundle.putString("location", s);
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  搜索新闻 咨询
     * @param root
     */
    public static void startSearchNewsFrg(BaseFragment root) {
        SearchNewsFrg frg = new SearchNewsFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  各种举报
     * @param root
     */
    public static void startReportNewsFrg(BaseFragment root, String discussId, String infoId, int type) {
        ReportNewsFrg frg = new ReportNewsFrg();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("discussId", discussId);
        bundle.putString("infoId", infoId);
        frg.setArguments(bundle);
        root.start(frg);
    }
    public static void startReportNewsFrg(BaseFragment root, String videoId, int type) {
        ReportNewsFrg frg = new ReportNewsFrg();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("videoId", videoId);
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  我的生日趴
     * @param root
     */
    public static void startProneFrg(BaseFragment root) {
        ProneFrg frg = new ProneFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  消息
     * @param root
     */
    public static void startMessageFrg(BaseFragment root) {
        MessageFrg frg = new MessageFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  新闻详情
     * @param infoId
     */
    public static void startNewsDescAct(String infoId, DataBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("id", infoId);
        bundle.putString("bean", new Gson().toJson(bean));
        ActivityUtils.startActivity(bundle, NewsDescAct.class);
    }

    /**
     *  生日趴
     */
    public static void startVideoAct(int isVideoType) {
        Bundle bundle = new Bundle();
        bundle.putInt("isVideoType", isVideoType);
        ActivityUtils.startActivity(bundle, VideoAct.class);
    }

    /**
     *  录制视频
     */
    public static void startRecordedAct(Activity act){
//        Intent intent = new Intent(act, RecordedActivity.class);
//        act.startActivityForResult(intent, 1);
    }

    /**
     *  视频发布
     */
    public static void startReleaseAct(){
        ActivityUtils.startActivity(ReleaseAct.class);
    }

}