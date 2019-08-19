package com.yc.mema.bean;

import com.baidu.location.Address;

public class AddressBean {

    private static class LazyHolder {
        private static final AddressBean INSTANCE = new AddressBean();
    }
    private AddressBean() {
    }
    public static final AddressBean getInstance() {
        return AddressBean.LazyHolder.INSTANCE;
    }

    private double location;//经度
    private double latitude;//纬度

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLocation() {
        return location;
    }

    public void setLocation(double location) {
        this.location = location;
    }

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
