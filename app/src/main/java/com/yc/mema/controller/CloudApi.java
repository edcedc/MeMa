package com.yc.mema.controller;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okrx2.adapter.ObservableBody;
import com.lzy.okrx2.adapter.ObservableResponse;
import com.yc.mema.bean.BaseListBean;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.BaseResponseSuccessBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.JsonCallback;
import com.yc.mema.callback.JsonConvert;
import com.yc.mema.callback.NewsCallback;
import com.yc.mema.utils.Constants;
import com.yc.mema.utils.cache.ShareSessionIdCache;

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
            "192.168.1.132:8080";


    public static final String SERVLET_URL = "http://" +
            url + "/brithday/";

     public static final String SERVLET_IMG_URL = SERVLET_URL + "attach/showPic?attachId=";

    public static final String TEST_URL = ""; //测试

    private static final String TAG = "CloudApi";

    private CloudApi() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     *  获取引导页列表
     */
    public static final String guideGetDuideList = "guide/getDuideList";

    /**
     *  获取福利轮播图
     */
    public static final String welfareGetWelfareLunList = "welfare/getAdvertList";

    /**
     *  获取资讯广告列表
     */
    public static final String informationGetInfoAdvertList = "information/getInfoAdvertList";


    /**
     *  获取资讯分类信息列表
     */
    public static final String informationGetInfoSortList = "information/getInfoSortList";


    /**
     * 更新/保存用户
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> userSaveUser(String head, String nickName, String birthday, String sex, String parentId, String mema, String updataMema) {
        PostRequest<BaseResponseBean<DataBean>> post = OkGo.post(SERVLET_URL + "user/saveUser");
        if (!StringUtils.isEmpty(head)){
            post.params("file", new File(head));
        }
        return post
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
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
     * 重置密码
     */
    public static Observable<Response<BaseResponseBean>> userResetPwd(String iphone, String password, String vercoed) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/resetPwd")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("iphone", iphone)
                .params("vercoed", vercoed)
                .params("password", password)
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
    public static Observable<Response<BaseResponseBean<DataBean>>> feedbackSaveFeedback(String iphone, String context) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "feedback/saveFeedback")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("iphone", iphone)
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
     * 登陆接口
     */
    public static Observable<JSONObject> userLogin(String mobile, String password) {
        return OkGo.<JSONObject>post(SERVLET_URL + "user/pLogin")
                .params("userName", mobile)
                .params("password", password)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 查找用户
     */
    public static Observable<JSONObject> userFindByUser() {
        LogUtils.e(ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId(), ShareSessionIdCache.getInstance(Utils.getApp()).getUserId());
        return OkGo.<JSONObject>get(SERVLET_URL + "user/findByUser")
                .headers("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new JsonConvert<JSONObject>() {})
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 手机用户注册账号
     */
    public static Observable<Response<BaseResponseBean>> userResgist(String phone, String password, String code) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/pResgist")
                .params("password", password)
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
     * 获取验证码
     *  验证码类型（1注册 4找回密码 8绑定手机）
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
     * 获取评论列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> informationGetInfoDiscussList(int pageNumber, String infoId) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "information/getInfoDiscussList")
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("infoId", infoId)
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
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> welfareGetWelfareList(String county, String search, int itemize, int pageNumber) {
        GetRequest<BaseResponseBean<BaseListBean<DataBean>>> request = OkGo.get(SERVLET_URL + "welfare/getWelfareList");
        if (itemize != 0){
            request.params("itemize", itemize);
        }
        if (!StringUtils.isEmpty(search)){
            request.params("like", itemize);
        }
        return request
                .params("county", county)
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
     *  获取微信登陆返回值
     */
    public static Observable<JSONObject> wxLogin(String openid, String access_token){
        return OkGo.<JSONObject>get("https://api.weixin.qq.com/sns/userinfo")
                .params("access_token", access_token)
                .params("openid", openid)
                .converter(new JsonConvert<JSONObject>() {})
                .adapt(new ObservableBody<>())
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
     * 通用list数据
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> list(int pageNumber, String url) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + url)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("sessionId", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
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
                .params("sessionId", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }
}