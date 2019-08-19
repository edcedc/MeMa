package com.yc.mema.presenter;

import android.app.AlertDialog;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lzy.okgo.model.Response;
import com.yc.mema.R;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.event.ReleaseInEvent;
import com.yc.mema.impl.ReleaseContract;
import com.yc.mema.utils.FileSaveUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/31
 * Time: 16:55
 */
public class ReleasePresenter extends ReleaseContract.Presenter{

     private final String bucketName = "memashejiao";
     private final String stsServer = "https://memashejiao.oss-cn-shenzhen.aliyuncs.com/";//拼接地址查看的
     private final String mRegion = "深圳";
     private final String STS_SERVER_URL = "http://*.*.*.*:*/sts/getsts";//STS 地址
     private OSSAsyncTask task;

    @Override
    public void onRelease(String text, String path, String suolue) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(path)) {
            showToast(act.getString(R.string.error_));
            return;
        }
        mView.showLoading();
        String abridge = FileSaveUtils.save(act, FileSaveUtils.getVideoThumbnail(path), TimeUtils.getNowMills() + "");
        String[] splt = path.split("/");
        String pathName = splt[splt.length - 1];
        String newOssEndpoint = getOssEndpoint();
        // 在移动端建议使用STS的方式初始化OSSClient。
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("LTAIYrlRBklXtcsw", "dCnWCbqR4B9oSeQtaLmEdDk5b0aHIt", "");
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(60 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(60 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个。
        conf.setMaxErrorRetry(3); // 失败后最大重试次数，默认2次。
        OSS oss = new OSSClient(act, newOssEndpoint, credentialProvider, conf);
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(bucketName, pathName, path);

        // 异步上传时可以设置进度回调。
        put.setProgressCallback((request, currentSize, totalSize) ->{
                //                LogUtils.e("currentSize: " + currentSize + " totalSize: " + totalSize)
            }
        );

        task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                //https://mema-oss.oss-cn-beijing.aliyuncs.com/IMG_4508.MP4
                videoSaveVideo(stsServer + pathName, abridge, text);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                mView.hideLoading();
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    LogUtils.e(clientExcepion.getMessage());
                }
                if (serviceException != null) {
                    // 服务异常。
                    LogUtils.e("ErrorCode", serviceException.getErrorCode());
                    LogUtils.e("RequestId", serviceException.getRequestId());
                    LogUtils.e("HostId", serviceException.getHostId());
                    LogUtils.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

        // task.waitUntilFinished(); // 等待任务完成。
    }

    @Override
    public void onDestroy() {
        if (task != null){
            task.cancel(); // 可以取消任务。
        }
    }

    protected String getOssEndpoint() {
        String ossEndpoint = "";
        if (mRegion.equals("杭州")) {
            ossEndpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        } else if (mRegion.equals("青岛")) {
            ossEndpoint = "http://oss-cn-qingdao.aliyuncs.com";
        } else if (mRegion.equals("北京")) {
            ossEndpoint = "http://oss-cn-beijing.aliyuncs.com";
        } else if (mRegion.equals("深圳")) {
            ossEndpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        } else if (mRegion.equals("美国")) {
            ossEndpoint = "http://oss-us-west-1.aliyuncs.com";
        } else if (mRegion.equals("上海")) {
            ossEndpoint = "http://oss-cn-shanghai.aliyuncs.com";
        } else {
            new AlertDialog.Builder(act).setTitle("错误的区域").setMessage(mRegion).show();
        }
        return ossEndpoint;
    }

    private void videoSaveVideo(String video, String file, String context){
        LogUtils.e(video);
        CloudApi.videoSaveVideo(video, file, context)
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            EventBus.getDefault().post(new ReleaseInEvent(baseResponseBeanResponse.body().result));
                            act.finish();
                        }
                        showToast(baseResponseBeanResponse.body().description);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

}
