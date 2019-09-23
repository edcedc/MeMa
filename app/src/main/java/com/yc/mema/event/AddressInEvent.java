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

    public int type;

    public String parentId, address, allAddress;

    public AddressInEvent(String parentId, String address, int type) {
        this.parentId = parentId;
        this.address = address;
        this.type = type;
    }

    public AddressInEvent(String parentId, String address, int type, String allAddress) {
        this.parentId = parentId;
        this.address = address;
        this.type = type;
        this.allAddress = allAddress;
    }



}
