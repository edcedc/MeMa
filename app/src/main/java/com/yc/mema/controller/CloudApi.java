package com.yc.mema.controller;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okrx2.adapter.ObservableBody;
import com.lzy.okrx2.adapter.ObservableResponse;
import com.yc.mema.base.TentryBean;
import com.yc.mema.base.User;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.bean.BaseListBean;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.JsonCallback;
import com.yc.mema.callback.JsonConvert;
import com.yc.mema.callback.NewsCallback;
import com.yc.mema.utils.Constants;
import com.yc.mema.utils.cache.ShareSessionIdCache;
import com.yc.mema.view.VideoFrg;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：yc on 2018/6/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class CloudApi {

    private static final String url =
//            "192.168.1.143";
//            "119.23.111.246:8080";
            "47.106.179.240";

    public static final String SERVLET_URL = "http://" +
            url + ":8080/brithday/";

    //物流地址
    public static final String WULIU_URL = "http://" +
            url + "/share/#/logistics?expCode=";

    //分享地址
    public static final String SHARE_URL = "http://" +
            url +
            "/share/#/code?userId=" + User.getInstance().getUserId();

    public static final String SERVLET_IMG_URL = SERVLET_URL + "attach/showPic?attachId=";

    public static final String TEST_URL = ""; //测试

    private static final String TAG = "CloudApi";

    private CloudApi() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 获取引导页列表
     */
    public static final String guideGetDuideList = "guide/getDuideList";

    /**
     * 获取福利轮播图
     */
    public static final String welfareGetWelfareLunList = "welfare/getAdvertList";

    /**
     * 获取商家帮助信息列表
     */
    public static final String businessGetGoodsHelps = "business/getGoodsHelps";

    /**
     * 获取商城轮播图列表
     */
    public static final String goodSpuGetGoodsCarouselList = "goodSpu/getGoodsCarouselList";

    /**
     * 获取资讯广告列表
     */
    public static final String informationGetInfoAdvertList = "information/getInfoAdvertList";


    /**
     * 获取资讯分类信息列表
     */
    public static final String informationGetInfoSortList = "information/getInfoSortList";

    /**
     * 获取我的福利收藏
     */
    public static final String welfareGetWelCollectList = "welfare/getWelCollectList";


    /**
     * 获取我的资讯收藏
     */
    public static final String informationGetInfoPraList = "information/getInfoPraList";

    /**
     * 获取我的视频收藏
     */
    public static final String videoGetVideoCollList = "video/getVideoCollList";

    /**
     * 获取收获地址列表
     */
    public static final String userGetUserAddresses = "user/getUserAddresses";


    /**
     * 获取福利分类列表
     */
    public static final String welfareGetWelfareClassifys = "welfare/getWelfareClassifys";


    /**
     * 获取首页信息
     */
    public static final String welfareGetHomeClassify = "welfare/getHomeClassify";


    /**
     * 更新/保存用户
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> userSaveUser(String head, String nickName, String birthday, String sex, String parentId, String mema, String updataMema) {
        PostRequest<BaseResponseBean<DataBean>> post = OkGo.post(SERVLET_URL + "user/saveUser");
//        PostRequest<BaseResponseBean<DataBean>> post = OkGo.post("http://jj123.nat300.top/adv_chain/api/user/update");
        if (!StringUtils.isEmpty(head)) {
            post.params("headUrl", new File(head));
//            post.params("sessionId", "41966fc3bbd94618accb98e23d386877");
        }
        return post
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", User.getInstance().getUserId())
                .params("nickName", nickName)
                .params("birthday", birthday)
                .params("sex", sex)
                .params("county", parentId)
                .params("mema", mema)
                .params("updataMema", updataMema)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑备忘录
     */
    public static Observable<Response<BaseResponseBean>> bookSaveBook(String brithday, String frendName) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "book/saveBook")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("brithday", brithday)
                .params("frendName", frendName)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑商品顶级评论
     */
    public static Observable<Response<BaseResponseBean>> goodSpuSaveGoodsDiscuss(String goodId, String orderId, int starLevel, String content, List<LocalMedia> localMediaList) {
        PostRequest<BaseResponseBean> post = OkGo.post(SERVLET_URL + "goodSpu/saveGoodsDiscuss");
        if (localMediaList != null && localMediaList.size() != 0) {
            for (LocalMedia media : localMediaList) {
                post.params("files", new File(media.getPath()));
            }
        }
        return post
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("goodId", goodId)
                .params("orderId", orderId)
                .params("starLevel", starLevel)
                .params("content", content)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑商家
     */
    public static Observable<Response<BaseResponseBean<String>>> businessSaveBusiness(List<LocalMedia> localMediaList) {
        PostRequest<BaseResponseBean<String>> post = OkGo.post(SERVLET_URL + "business/saveBusiness");
        if (localMediaList != null && localMediaList.size() != 0) {
            for (LocalMedia media : localMediaList){
                post.params("files", new File(media.getPath()));
            }
        }
        return post
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("userName", TentryBean.getInstance().name)
                .params("imgType", "3,5,4,2,1")
                .params("phone", TentryBean.getInstance().phone)
                .params("card", TentryBean.getInstance().userId)
                .params("creditCode", TentryBean.getInstance().num)
                .params("bank", TentryBean.getInstance().bankName)
                .params("defaultPhone", TentryBean.getInstance().bankPhone)
                .params("bankNum", TentryBean.getInstance().bankId)
                .params("county", TentryBean.getInstance().address.split(",")[2])
                .params("address", TentryBean.getInstance().addressDesc)
                .params("type", TentryBean.getInstance().type)
                .params("classifyId", TentryBean.getInstance().category)
                .params("area", TentryBean.getInstance().shopArea)
                .params("scope", TentryBean.getInstance().shopScope)
                .params("handle", 0)
                .converter(new NewsCallback<BaseResponseBean<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<String>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 设置默认地址
     */
    public static Observable<Response<BaseResponseBean>> userSetDefaultAddress(String id) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/setDefaultAddress")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("id", id)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 删除收获地址
     */
    public static Observable<Response<BaseResponseBean>> userDelUserAddress(String ids) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/delUserAddress")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("ids", ids)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑收货地址
     */
    public static Observable<Response<BaseResponseBean>> userSaveUserAddress(String id, String userName, String iphone, String counties, String address, String parentId) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/saveUserAddress")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("addressId", id)
                .params("userName", userName)
                .params("iphone", iphone)
                .params("counties", counties)
                .params("address", address)
                .params("county", parentId)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑申请信息
     */
    public static Observable<Response<BaseResponseBean>> agentSaveAgent(String userName, String wechatNum, String iphone, String vercoed, String inviteCode, String roleId,
                                                                        String industry, String county, String directTeam, String mailbox,
                                                                        String imgZheng, String imgFan, String imgShou, String cardId) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "agent/saveAgent")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("userName", userName)
                .params("wechatNum", wechatNum)
                .params("iphone", iphone)
                .params("vercoed", vercoed)
                .params("inviteCode", inviteCode)
                .params("roleId", roleId)
                .params("industry", industry)
                .params("county", county)
                .params("directTeam", directTeam)
                .params("mailbox", mailbox)
                .params("formData", new File(imgZheng))
                .params("formData", new File(imgFan))
                .params("formData", new File(imgShou))
                .params("handle", 1)
                .params("cardId", cardId)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 绑定手机号码
     */
    public static Observable<Response<BaseResponseBean>> userBuildPhone(String iphone, String vercoed) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/BuildPhone")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("iphone", iphone)
                .params("vercoed", vercoed)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 判断是否处理过审核信息
     * 1审核中  2同意  3拒绝
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> agentGetUserAgent() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "agent/getUserAgent")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new JsonCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取一条商家信息
     *      处理状态「 0待处理 1同意 2失败」
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> businessGetBusiness() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "business/getBusiness")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new JsonCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取一条订单信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> goodSpuGetGoodsOrder(String id) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "goodSpu/getGoodsOrder")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("id", id)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑订单
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> goodSpuSaveGoodsOrder(String goodId, String addressId, String goodSku, int goodNum, double price) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "goodSpu/saveGoodsOrder")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("goodId", goodId)
                .params("addressId", addressId)
                .params("goodSku", goodSku)
                .params("goodNumber", goodNum)
                .params("price", price)
                .params("isAppraise", 0)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }
    public static Observable<Response<BaseResponseBean<DataBean>>> goodSpuSaveGoodsOrder(String goodId, int status) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "goodSpu/saveGoodsOrder")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("orderId", goodId)
                .params("status", status)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取一条商品信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> goodSpuGetGoodsSpu(String id) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "goodSpu/getGoodsSpu")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("id", id)
                .converter(new JsonCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 重置密码
     */
    public static Observable<Response<BaseResponseBean>> userResetPwd(String iphone, String password, String vercoed) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/resetPwd")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("iphone", iphone)
                .params("vercoed", vercoed)
                .params("password", EncryptUtils.encryptMD5ToString(password).toLowerCase())
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取一条福利信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> welfareGetWelfare(String id) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "welfare/getWelfare")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("welId", id)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑意见反馈信息
     */
    public static Observable<Response<BaseResponseBean>> feedbackSaveFeedback(String iphone, String context) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "feedback/saveFeedback")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("iphone", iphone)
                .params("context", context)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 登陆接口
     */
    public static Observable<JSONObject> userLogin(String mobile, String password) {
        return OkGo.<JSONObject>post(SERVLET_URL + "user/pLogin")
                .params("userName", mobile)
                .params("password", EncryptUtils.encryptMD5ToString(password).toLowerCase())
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 角色下拉框
     */
    public static Observable<JSONObject> roleGetRoleSelect() {
        return OkGo.<JSONObject>get(SERVLET_URL + "role/getAgentSelect")
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 统一下单
     */
    public static Observable<JSONObject> payWeChatPay(String orderId) {
        return OkGo.<JSONObject>post(SERVLET_URL + "pay/WeChatPay")
                .params("orderId", orderId)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 查找用户
     */
    public static Observable<JSONObject> userFindByUser() {
        return OkGo.<JSONObject>get(SERVLET_URL + "user/findByUser")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 手机用户注册账号
     */
    public static Observable<Response<BaseResponseBean>> userResgist(String phone, String password, String code) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/pResgist")
                .params("password", EncryptUtils.encryptMD5ToString(password).toLowerCase())
                .params("userName", phone)
                .params("vercoed", code)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 福利点赞
     */
    public static Observable<Response<BaseResponseBean>> welfareWelPraise(String welId, int status) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "welfare/welPraise")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("welId", welId)
                .params("status", status)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 视频点赞
     */
    public static Observable<Response<BaseResponseBean>> videoVideoPraise(String videoId, int status) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "video/videoPraise")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("videoId", videoId)
                .params("status", status)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 视频收藏
     */
    public static Observable<Response<BaseResponseBean>> videoVideoCollect(String videoId, int status) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "video/videoCollect")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("ids", videoId)
                .params("status", status)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 商品收藏
     */
    public static Observable<Response<BaseResponseBean>> goodSpuGoodCollect(String ids, int status) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "goodSpu/GoodCollect")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("ids", ids)
                .params("status", status)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 福利收藏
     */
    public static Observable<Response<BaseResponseBean>> welfareWelCollect(String welId, int status) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "welfare/welCollect")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("ids", welId)
                .params("status", status)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 评论点赞
     */
    public static Observable<Response<BaseResponseBean>> informationSaveInfoDispra(String discussId, int status) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "information/saveInfoDispra")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("discussId", discussId)
                .params("status", status)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 视频评论点赞
     */
    public static Observable<Response<BaseResponseBean>> videoSaveInfoDispra(String discussId, int status) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "video/saveInfoDispra")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("discussId", discussId)
                .params("status", status)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 资讯点赞
     */
    public static Observable<Response<BaseResponseBean>> informationInfoPraise(String infoId, int status) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "information/infoPraise")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("ids", infoId)
                .params("status", status)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取验证码
     * 验证码类型（1注册 4找回密码 8绑定手机）
     */
    public static Observable<Response<BaseResponseBean>> userSendVcode(String iphone, int vcodeType) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/sendVcode")
                .params("iphone", iphone)
                .params("vcodeType", vcodeType)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取一条资讯信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> informationGetInformation(String infoId) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "information/getInformation")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("infoId", infoId)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> informationSaveInfoDiscuss(String infoId, String context) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "information/saveInfoDiscuss")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("infoId", infoId)
                .params("context", context)
                .params("ejContext", EncodeUtils.base64Encode2String(context.getBytes()))
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑视频评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> videoSaveVideoDiscuss(String videoId, String context) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "video/saveVideoDiscuss")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("videoId", videoId)
                .params("context", context)
                .params("ejContext", EncodeUtils.base64Encode2String(context.getBytes()))
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑子级评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> informationSaveDiscussChild(String infoId, String discussId, String context, String pUserId) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "information/saveDiscussChild")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("infoId", infoId)
                .params("discussId", discussId)
                .params("puserId", pUserId)
                .params("context", context)
                .params("ejContext", EncodeUtils.base64Encode2String(context.getBytes()))
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 视频保存编辑子级评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> videoSaveChildDis(String videoId, String discussId, String context, String pUserId) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "video/saveChildDis")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("videoId", videoId)
                .params("discussId", discussId)
                .params("puserId", pUserId)
                .params("context", context)
                .params("ejContext", EncodeUtils.base64Encode2String(context.getBytes()))
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 删除视频
     */
    public static Observable<Response<BaseResponseBean>> videoDelVideo(String videoId) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "video/delVideo")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("ids", videoId)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 删除备忘录
     */
    public static Observable<Response<BaseResponseBean>> bookDelBook(String ids) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "book/delBook")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("ids", ids)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取资讯信息列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> informationGetInformationList(int pageNumber, String sortId, String like) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "information/getInformationList")
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("sortId", sortId)
                .params("like", like)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取订单列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> goodSpuGetGoodsOrdersList(int pageNumber, int status) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "goodSpu/getGoodsOrdersList")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("status", status)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取评论列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> informationGetInfoDiscussList(int pageNumber, String infoId, int type) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "information/getInfoDiscussList")
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("infoId", infoId)
                .params("type", type)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取商品评论列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> goodSpuGetGoodsDiscussList(int pageNumber, String id) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "goodSpu/getGoodsDiscussList")
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("goodId", id)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取福利信息列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> welfareGetWelfareList(
            String county, String search, int itemize, int pageNumber, int low, int up, int type) {
        GetRequest<BaseResponseBean<BaseListBean<DataBean>>> request = OkGo.get(SERVLET_URL + "welfare/getWelfareList");
        if (itemize != 0) {
            request.params("itemize", itemize);
        }
        if (!StringUtils.isEmpty(search)) {
            request.params("like", search);
        }
        return request
                .params("type", type)
//                .params("low", low)
//                .params("up", up)
                .params("ids", county)
                .params("longitude", AddressBean.getInstance().getLocation())
                .params("latitude", AddressBean.getInstance().getLatitude())
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取微信登陆返回值
     */
    public static Observable<JSONObject> wxLogin(String openid, String access_token) {
        return OkGo.<JSONObject>get("https://api.weixin.qq.com/sns/userinfo")
                .params("access_token", access_token)
                .params("openid", openid)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取评论点赞通知列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> sysmsgGetRingList(int pageNumber) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "sysmsg/getRingList")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取原因列表
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> complainGetComplainSos(String complainId) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "complain/getComplainSos")
                .params("complainId", complainId)
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取备忘录列表
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> bookGetBookList(String nowDay) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "book/getBookList")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("nowDay", nowDay == null ? null : nowDay.trim().split("-")[1])
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取一条投诉模块信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> complainGetComplain(String complainId) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "complain/getComplain")
                .params("complainId", complainId)
                .converter(new JsonCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑视频
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> videoSaveVideo(String video, String file, String context) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "video/saveVideo")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("path", video)
                .params("file", new File(file))
                .params("context", context)
                .converter(new JsonCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑视频反馈
     */
    public static Observable<Response<BaseResponseBean>> videoSaveVideoBack(String videoId, String soId) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "video/saveVideoBack")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("soId", soId)
                .params("videoId", videoId)
                .params("handle", 1)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取视频评论列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> videoGetVideoDisList(String videoId, int pageNumber) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "video/getVideoDisList")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("page", pageNumber)
                .params("size", 200)
                .params("videoId", videoId)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取视频列表
     */
    public static final String videoGetVideoList = "video/getVideoList";

    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> videoGetVideoList(int pageNumber, int type) {
        String url = "";
        switch (type) {
            case VideoFrg.NORMAL_VIDEO:
            case VideoFrg.MY_VIDEO:
                url = videoGetVideoList;
                break;
            case VideoFrg.COLL_VIDEO:
                url = videoGetVideoCollList;
                break;
        }
        GetRequest<BaseResponseBean<BaseListBean<DataBean>>> request = OkGo.get(SERVLET_URL + url);
        switch (type) {
            case VideoFrg.MY_VIDEO:
                request.params("myId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId());
                break;
        }
        return request
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 系统消息列表
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> sysmsgGetSysmsgList() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "sysmsg/getSysmsgList")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑资讯反馈信息
     */
    public static Observable<Response<BaseResponseBean>> informationSaveInfoBack(List<LocalMedia> localMediaList, String infoId, String discussId, String soId, String content, String welId) {
        PostRequest<BaseResponseBean> post = OkGo.post(SERVLET_URL + "information/saveInfoBack");
        if (localMediaList != null && localMediaList.size() != 0) {
            for (LocalMedia media : localMediaList) {
                post.params("files", new File(media.getPath()));
            }
        }
        return post
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("infoId", infoId)
                .params("discussId", discussId)
                .params("soId", soId)
                .params("handle", 1)
                .params("context", content)
                .params("welId", welId)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存福利反馈信息
     */
    public static Observable<Response<BaseResponseBean>> welfareSaveWelfareBack(List<LocalMedia> localMediaList, String infoId, String discussId, String soId, String content, String welId) {
        PostRequest<BaseResponseBean> post = OkGo.post(SERVLET_URL + "welfare/saveWelfareBack");
        if (localMediaList != null && localMediaList.size() != 0) {
            for (LocalMedia media : localMediaList) {
                post.params("files", new File(media.getPath()));
            }
        }
        return post
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("infoId", infoId)
                .params("discussId", discussId)
                .params("soId", soId)
                .params("handle", 1)
                .params("context", content)
                .params("welId", welId)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 根据父级ID查询
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> regionGetRegion(String parentId) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "region/getRegion")
                .params("parentId", parentId)
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取商品分类推荐
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> goodSpuGetGoodsCategoryList(String parentId) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "goodSpu/getGoodsCategoryList")
                .params("parentId", parentId)
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取商品分类推荐
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> goodSpuGetNineGoodsCategory(String id) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "goodSpu/getNineGoodsCategory")
                .params("parentId", id)
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 首页分类
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> welfareGetWelfareClassifys() {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "welfare/getWelfareClassifys")
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取商品列表信息
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> goodSpuGetGoodsSpuList(int pageNumber, int type, String categoryId, int di, int gao, String like) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "goodSpu/getGoodsSpuList")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("type", type)
                .params("parentId", categoryId)
                .params("categoryId", categoryId)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("low", di)
                .params("up", gao)
                .params("like", like)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取福利分类列表
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> welfareGetWelfareClassifys(String classifyId) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "welfare/getWelfareClassifys")
                .params("classifyId", classifyId)
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取首页信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> welfareGetHomeClassify(String ids) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "welfare/getHomeClassify")
                .params("ids", ids)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 通用list数据
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> list(int pageNumber, String url) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + url)
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 通用list 2
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> list2(String url) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + url)
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

}