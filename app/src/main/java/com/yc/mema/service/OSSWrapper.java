package com.yc.mema.service;


import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.yc.mema.mar.MyApplication;

public class OSSWrapper {

    private static final OSSWrapper WRAPPER = new OSSWrapper();
    private OSSClient mClient = null;
    private static final String STS_INFO_URL = "http://*.*.*.*:****/sts/getsts";
    private static final String OSS_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";

    private OSSWrapper() {
        OSSAuthCredentialsProvider authCredentialsProvider = new OSSAuthCredentialsProvider(STS_INFO_URL);
        mClient = new OSSClient(MyApplication.getInstance(), OSS_ENDPOINT, authCredentialsProvider);
    }

    public static OSSWrapper sharedWrapper() {
        return WRAPPER;
    }

    public OSSClient getClient() {
        return mClient;
    }
}
