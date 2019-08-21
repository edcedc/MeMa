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
    private String country;
    private String province;
    private String city;
    private String district;
    private String address;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
