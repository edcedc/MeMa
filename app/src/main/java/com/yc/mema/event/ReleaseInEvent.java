package com.yc.mema.event;

import com.yc.mema.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/19
 * Time: 22:00
 */
public class ReleaseInEvent {

    public DataBean bean;

    public ReleaseInEvent(DataBean bean) {
        this.bean = bean;
    }
}
