package com.yc.mema.base;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/27
 * Time: 17:34
 */
public class TentryBean {

    private static class LazyHolder {
        private static final TentryBean INSTANCE = new TentryBean();
    }
    private TentryBean() {
    }
    public static final TentryBean getInstance() {
        return TentryBean.LazyHolder.INSTANCE;
    }

    public String name;
    public String phone;
    public String userId;
    public String num;
    public String bankName;
    public String bankPhone;
    public String bankId;
    public String address;
    public String addressDesc;
    public int type;
    public String category;
    public String shopArea;
    public String shopScope;

    public void clear(){
        name = null;
        phone = null;
        userId = null;
        num = null;
        bankName = null;
        bankPhone = null;
        bankId = null;
        address = null;
        addressDesc = null;
        type = -1;
        category = null;
        shopArea = null;
        shopScope = null;
    }

}
