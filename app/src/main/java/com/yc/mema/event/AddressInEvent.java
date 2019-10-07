package com.yc.mema.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/1
 * Time: 19:56
 */
public class AddressInEvent {

    public static final int GIFT_TYPE = 0;//礼包
    public static final int USER_INFP_TYPE = 1;//用户更新
    public static final int APPLY_TYPE = 2;//申请代理人
    public static final int HARVEST_ADDRESS = 3;//收获地址
    public static final int TENTRY = 4;//商家入驻
    public static final int LIWU = 5;//首页礼包

    public int type;

    public String parentId;

    public AddressInEvent(int type, String parentId) {
        this.type = type;
        this.parentId = parentId;
    }

}
