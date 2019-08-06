package com.yc.mema.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2017/8/17.
 */

public class DataBean implements Serializable {

    private String name;
    private int img = -1;
    private boolean isSelect = false;
    private int position;
    private double price;
    private String title;
    private String content;
    private String image;
    private int type;
    private String id;
    private boolean allSelected = false;
    private String sortLetters;//显示数据拼音的首字母
    private String url;
    private boolean isPlay = true;
    private String imageId;
    private String attachId;
    private String walTitle;
    private String pcyAdd;
    private String discount;
    private String welId;
    private int browseCount;//游览人数
    private String iphone;//商家电话
    private int collectCount;//收藏人数
    private int praiseCount;//点赞人数
    private String businessTime;//营业时间
    private String address;//具体地址
    private String regionName;//地址名称
    private int regionLevel;//地址等级 3最大
    private String regionId;
    private String sortName;//咨询标题
    private String sortId;
    private String createTime;
    private String infoId;
    private int praise;//点赞数量
    private int discuss;//评论数量
    private String context;
    private String birthday;
    private String nickName;
    private String headUrl;
    private String mema;
    private int sex;
    private String parentId;
    private String discussId;
    private String puserId;
    private String pNickName;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public String getpNickName() {
        return pNickName;
    }

    public String getpUserId() {
        return puserId;
    }

    public String getDiscussId() {
        return discussId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getNickName() {
        return nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public String getMema() {
        return mema;
    }

    public int getSex() {
        return sex;
    }

    public String getContext() {
        return context;
    }

    public int getDiscuss() {
        return discuss;
    }

    public int getPraise() {
        return praise;
    }

    public String getInfoId() {
        return infoId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getSortId() {
        return sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public String getRegionId() {
        return regionId;
    }

    public int getRegionLevel() {
        return regionLevel;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getAddress() {
        return address;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public String getIphone() {
        return iphone;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public String getWelId() {
        return welId;
    }

    public String getAttachId() {
        return attachId;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPcyAdd() {
        return pcyAdd;
    }

    public String getWalTitle() {
        return walTitle;
    }

    public String getImageId() {
        return imageId;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }


    public List<DataBean> welfareImgs;

    public List<DataBean> getWelfareImgs() {
        return welfareImgs;
    }

    public List<DataBean> informationImg;

    public List<DataBean> getInformationImg() {
        return informationImg;
    }

    public List<DataBean> list = new ArrayList<>();

    public void setList(List<DataBean> list) {
        this.list = list;
    }

    public List<DataBean> getList() {
        return list;
    }
}