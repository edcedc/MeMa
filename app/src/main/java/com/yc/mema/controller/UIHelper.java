package com.yc.mema.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.mema.MainActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.view.AboutFrg;
import com.yc.mema.view.AccountFrg;
import com.yc.mema.view.AddBirthdayRecordsFrg;
import com.yc.mema.view.AddressFrg;
import com.yc.mema.view.ApplyFrg;
import com.yc.mema.view.BingPhoneFrg;
import com.yc.mema.view.BirthdayRecordsFrg;
import com.yc.mema.view.BlackListFrg;
import com.yc.mema.view.CategoryFrg;
import com.yc.mema.view.ChangePwdFrg;
import com.yc.mema.view.CollectionFrg;
import com.yc.mema.view.ComplaintFrg;
import com.yc.mema.view.EditAddressFrg;
import com.yc.mema.view.FiveFrg;
import com.yc.mema.view.ShopCategoryFrg;
import com.yc.mema.view.TentryChildOneFrg;
import com.yc.mema.view.TentryFrg;
import com.yc.mema.view.TentryHelpFrg;
import com.yc.mema.view.act.AddressAct;
import com.yc.mema.view.act.BusinessDescAct;
import com.yc.mema.view.act.CustomizedDescAct;
import com.yc.mema.view.act.EvaluateAct;
import com.yc.mema.view.ForgetFrg;
import com.yc.mema.view.HeadFrg;
import com.yc.mema.view.HelpFrg;
import com.yc.mema.view.ImmediatelyFrg;
import com.yc.mema.view.InformationFrg;
import com.yc.mema.view.MainFrg;
import com.yc.mema.view.MemaFrg;
import com.yc.mema.view.MemorandumFrg;
import com.yc.mema.view.MessageFrg;
import com.yc.mema.view.MsgFrg;
import com.yc.mema.view.OrderDescFrg;
import com.yc.mema.view.OrderListFrg;
import com.yc.mema.view.OtherCategoryFrg;
import com.yc.mema.view.PaySuccessFrg;
import com.yc.mema.view.PrivacyFrg;
import com.yc.mema.view.ProneFrg;
import com.yc.mema.view.ReportFrg;
import com.yc.mema.view.ReportNewsFrg;
import com.yc.mema.view.SearchGiftFrg;
import com.yc.mema.view.SearchNewsFrg;
import com.yc.mema.view.SearchShopFrg;
import com.yc.mema.view.ShopAddressFrg;
import com.yc.mema.view.SexFrg;
import com.yc.mema.view.ShareFrg;
import com.yc.mema.view.ShopCommentFrg;
import com.yc.mema.view.SystemDescFrg;
import com.yc.mema.view.UpdateNameFrg;
import com.yc.mema.view.UserInfoFrg;
import com.yc.mema.view.ZkingFrg;
import com.yc.mema.view.act.GiftAct;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.view.act.LoginAct;
import com.yc.mema.view.act.NewsDescAct;
import com.yc.mema.view.act.OrderListAct;
import com.yc.mema.view.act.ReleaseAct;
import com.yc.mema.view.act.ShopAct;
import com.yc.mema.view.act.ShopDescAct;
import com.yc.mema.view.act.VideoAct;
import com.yc.mema.view.act.SetAct;
import com.yc.mema.view.act.UserInfoAct;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


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
     * 登录
     */
    public static void startLoginAct() {
        ActivityUtils.startActivity(LoginAct.class);
    }

    /**
     * 找回密码
     *
     * @param root
     */
    public static void startForgetFrg(BaseFragment root) {
        ForgetFrg frg = new ForgetFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 完善信息
     *
     * @param root
     * @param data
     */
    public static void startInformationFrg(BaseFragment root, JSONObject data) {
        InformationFrg frg = new InformationFrg();
        Bundle bundle = new Bundle();
        bundle.putString("data", data.toString());
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * html
     */
    public static void startHtmlAct(int type, String url) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("url", url);
        ActivityUtils.startActivity(bundle, HtmlAct.class);
    }
    public static void startHtmlAct(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
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
     * y24
     */
    public static void startCollectionFrg(BaseFragment root) {
        CollectionFrg frg = new CollectionFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 个人信息
     *
     * @param root
     */
    public static void startUserInfoFrg(BaseFragment root) {
        UserInfoFrg frg = new UserInfoFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 修改头像
     */
    public static void startHeadFrg(BaseFragment root) {
        HeadFrg frg = new HeadFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 系统通知详情
     */
    public static void startSystemDescFrg(BaseFragment root, DataBean bean) {
        SystemDescFrg frg = new SystemDescFrg();
        Bundle bundle = new Bundle();
        bundle.putString("bean", new Gson().toJson(bean));
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 账号与安全
     */
    public static void startAccountFrg(BaseFragment root) {
        AccountFrg frg = new AccountFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 修改名字
     *
     * @param root
     */
    public static void startUpdateNameFrg(BaseFragment root) {
        UpdateNameFrg frg = new UpdateNameFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 么马号
     *
     * @param root
     */
    public static void startMemaFrg(BaseFragment root) {
        MemaFrg frg = new MemaFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 选择性别
     *
     * @param root
     */
    public static void startSexFrg(BaseFragment root) {
        SexFrg frg = new SexFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 设置地址
     */
    public static void startAddressAct(int startType) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", startType);
        ActivityUtils.startActivity(bundle, AddressAct.class);
    }

    /**
     * 我的二维码
     */
    public static void startZkingFrg(BaseFragment root) {
        ZkingFrg frg = new ZkingFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        Fragment fragment = root.getParentFragment();
        if (fragment == null) {
            root.start(frg);
        } else {
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }

    /**
     * 绑定手机号
     *
     * @param root
     */
    public static void startBingPhoneFrg(BaseFragment root) {
        BingPhoneFrg frg = new BingPhoneFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 修改密码
     *
     * @param root
     */
    public static void startChangePwdFrg(BaseFragment root) {
        ChangePwdFrg frg = new ChangePwdFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 支付成功
     *
     * @param root
     * @param allPrice
     */
    public static void startPaySuccessFrg(BaseFragment root, double allPrice) {
        PaySuccessFrg frg = new PaySuccessFrg();
        Bundle bundle = new Bundle();
        bundle.putDouble("price", allPrice);
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 消息通知
     *
     * @param root
     */
    public static void startMsgFrg(BaseFragment root) {
        MsgFrg frg = new MsgFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 隐私
     *
     * @param root
     */
    public static void startPrivacyFrg(BaseFragment root) {
        PrivacyFrg frg = new PrivacyFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 黑名单
     *
     * @param root
     */
    public static void startBlackListFrg(BaseFragment root) {
        BlackListFrg frg = new BlackListFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 记事本
     *
     * @param root
     */
    public static void startMemorandumFrgFrg(BaseFragment root) {
        MemorandumFrg frg = new MemorandumFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 帮助与反馈
     *
     * @param root
     */
    public static void startHelpFrg(BaseFragment root) {
        HelpFrg frg = new HelpFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 关于我们
     *
     * @param root
     */
    public static void startAboutFrg(BaseFragment root) {
        AboutFrg frg = new AboutFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 生日备忘录
     *
     * @param root
     */
    public static void startBirthdayRecordsFrg(BaseFragment root) {
        BirthdayRecordsFrg frg = new BirthdayRecordsFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 添加生日记录
     *
     * @param root
     */
    public static void startAddBirthdayRecordsFrg(BaseFragment root) {
        AddBirthdayRecordsFrg frg = new AddBirthdayRecordsFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 用户投诉反馈
     *
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
     * 投诉
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
     * 礼包 商家 福利
     *
     * @param welId
     */
    public static void startGiftAct(String welId) {
        Bundle bundle = new Bundle();
        bundle.putString("id", welId);
        ActivityUtils.startActivity(bundle, GiftAct.class);
    }

    /**
     * 商家店铺详情
     */
    public static void startCustomizedDescAct(DataBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("bean", new Gson().toJson(bean));
        ActivityUtils.startActivity(bundle, CustomizedDescAct.class);
    }

    /**
     * 搜索礼包
     *
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
     * 搜索新闻 咨询
     *
     * @param root
     */
    public static void startSearchNewsFrg(BaseFragment root) {
        SearchNewsFrg frg = new SearchNewsFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 各种举报
     *
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
     * 我的生日趴
     *
     * @param root
     */
    public static void startProneFrg(BaseFragment root) {
        ProneFrg frg = new ProneFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 消息
     *
     * @param root
     */
    public static void startMessageFrg(BaseFragment root) {
        MessageFrg frg = new MessageFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 新闻详情
     *
     * @param infoId
     */
    public static void startNewsDescAct(String infoId, DataBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("id", infoId);
        bundle.putString("bean", new Gson().toJson(bean));
        ActivityUtils.startActivity(bundle, NewsDescAct.class);
    }

    /**
     * 生日趴
     */
    public static void startVideoAct(int isVideoType, List<DataBean> listBean, int position) {
        Type type = new TypeToken<ArrayList<DataBean>>() {}.getType();
        String json = new Gson().toJson(listBean, type);
        Bundle bundle = new Bundle();
        bundle.putString("list", json);
        bundle.putInt("isVideoType", isVideoType);
        bundle.putInt("position", position);
        ActivityUtils.startActivity(bundle, VideoAct.class);
    }

    /**
     * 录制视频
     */
    public static void startRecordedAct(Activity act) {
//        Intent intent = new Intent(act, RecordedActivity.class);
//        act.startActivityForResult(intent, 1);
    }

    /**
     * 视频发布
     */
    public static void startReleaseAct() {
        ActivityUtils.startActivity(ReleaseAct.class);
    }

    /**
     * 推广分享
     *
     * @param root
     */
    public static void startShareFrg(BaseFragment root) {
        ShareFrg frg = new ShareFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 申请代理人城市
     */
    public static void startApplyFrg(BaseFragment root) {
        ApplyFrg frg = new ApplyFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 获取商品分类推荐
     *
     * @param root
     */
    public static void startMoreCategoryFrg(BaseFragment root) {
        CategoryFrg frg = new CategoryFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 获取商品分类更多
     *
     * @param root
     * @param categoryId
     * @param title
     */
    public static void startCategoryFrg(BaseFragment root, String categoryId, String title) {
        OtherCategoryFrg frg = new OtherCategoryFrg();
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        bundle.putString("title", title);
        frg.setArguments(bundle);
        Fragment fragment = root.getParentFragment();
        if (fragment == null) {
            root.start(frg);
        } else {
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }

    /**
     * 搜索商品
     */
    public static void startSearchShopFrg(BaseFragment root) {
        SearchShopFrg frg = new SearchShopFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        Fragment fragment = root.getParentFragment();
        if (fragment == null) {
            root.start(frg);
        } else {
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }

    /**
     * 商品详情
     */
    public static void startShopDescAct(String goodId) {
        Bundle bundle = new Bundle();
        bundle.putString("id", goodId);
        ActivityUtils.startActivity(bundle, ShopDescAct.class);
    }

    /**
     * 立即购买/确认订单
     */
    public static void startImmediatelyFrg(BaseFragment root, List<DataBean> listBean) {
        Type type = new TypeToken<ArrayList<DataBean>>() {
        }.getType();
        String json = new Gson().toJson(listBean, type);
        ImmediatelyFrg frg = new ImmediatelyFrg();
        Bundle bundle = new Bundle();
        bundle.putString("list", json);
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 查看商品全部评论
     *
     * @param root
     * @param id
     */
    public static void startShopCommentFrg(BaseFragment root, String id) {
        ShopCommentFrg frg = new ShopCommentFrg();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 我的收获地址
     *
     * @param root
     */
    public static void startShopAddressFrg(BaseFragment root) {
        ShopAddressFrg frg = new ShopAddressFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        Fragment fragment = root.getParentFragment();
        if (fragment == null) {
            root.start(frg);
        } else {
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }

    /**
     * 编辑/新增收获地址
     */
    public static void startEditAddressFrg(BaseFragment root, String id, DataBean bean) {
        EditAddressFrg frg = new EditAddressFrg();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("bean", new Gson().toJson(bean));
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 查看订单列表
     */
    public static void startOrderListAct(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        ActivityUtils.startActivity(bundle, OrderListAct.class);
    }

    /**
     * 商城
     */
    public static void startShopAct() {
        ActivityUtils.startActivity(ShopAct.class);
    }

    /**
     * 商家二级页面
     */
    public static void startBusinessDescAct() {
        Bundle bundle = new Bundle();
        ActivityUtils.startActivity(bundle, BusinessDescAct.class);
    }

    /**
     * 订单详情
     *
     * @param root
     */
    public static void startOrderDescFrg(BaseFragment root, String goodId) {
        OrderDescFrg frg = new OrderDescFrg();
        Bundle bundle = new Bundle();
        bundle.putString("id", goodId);
        frg.setArguments(bundle);
        Fragment fragment = root.getParentFragment();
        if (fragment == null) {
            root.start(frg);
        } else {
            ((OrderListFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }

    /**
     * 订单评价
     */
    public static void startEvaluateFrg(DataBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("bean", new Gson().toJson(bean));
        ActivityUtils.startActivity(bundle, EvaluateAct.class);
    }

    /**
     *  商家入驻
     */
    public static void starTentryFrg(BaseFragment root, int handle, String reason) {
        TentryFrg frg = new TentryFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        bundle.putInt("type", handle);
        bundle.putString("reason", reason);
        Fragment fragment = root.getParentFragment();
        if (fragment == null) {
            root.start(frg);
        } else {
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }

    /**
     *  商家经营
     * @param root
     */
    public static void startShopCategoryFrg(BaseFragment root) {
        ShopCategoryFrg frg = new ShopCategoryFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        Fragment fragment = root.getParentFragment();
        if (fragment == null) {
            root.start(frg);
        } else {
            ((TentryFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }

    /**
     *  商家入驻帮助
     * @param root
     */
    public static void startTentryHelpFrg(BaseFragment root) {
        TentryHelpFrg frg = new TentryHelpFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        Fragment fragment = root.getParentFragment();
        if (fragment == null) {
            root.start(frg);
        } else {
            ((TentryFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }
}