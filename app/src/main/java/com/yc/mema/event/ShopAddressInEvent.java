package com.yc.mema.event;

import com.yc.mema.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/10
 * Time: 16:54
 */
public class ShopAddressInEvent {

    public DataBean bean;

    public DataBean getBean() {
        return bean;
    }

    public ShopAddressInEvent(DataBean bean) {
        this.bean = bean;
    }
}
