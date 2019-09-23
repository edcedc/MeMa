package com.yc.mema.event;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class CameraInEvent {

    public static final int HEAD_CAMEAR = 0;//头像_相册
    public static final int HEAD_PHOTO = 1;//头像_相机
    public static final int USER_ZHENG = 2;//身份证 正
    public static final int USER_FAN = 3;//身份证 反
    public static final int USER_SHOU = 4;//身份证 手持
    private int request;

    private Object object;

    public int getRequest() {
        return request;
    }

    public Object getObject() {
        return object;
    }

    public CameraInEvent(int request, Object object) {
        this.request = request;
        this.object = object;
    }
}
