package com.yc.mema.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/1
 * Time: 19:56
 */
public class AddressInEvent {

    public String parentId, address;

    public String getAddress() {
        return address;
    }

    public String getParentId() {
        return parentId;
    }

    public AddressInEvent(String parentId, String address) {
        this.parentId = parentId;
        this.address = address;
    }
}
