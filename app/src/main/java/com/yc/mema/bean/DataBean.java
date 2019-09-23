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
    private String nickName;
    private String headUrl;
    private String mema;
    private int sex;
    private String parentId;
    private String discussId;
    private String puserId;
    private String pNickName;
    private String userId;
    private int isTrue;//0未1是
    private String complainName;
    private String complainId;
    private String soName;
    private String soId;
    private String childId;
    private int pIsTrue;
    private int cIsTrue;
    private int fIsTrue;
    private int isRead;//0未读 1已读
    private int goDay;
    private String brithday;
    private int inDay;
    private String video;
    private String videoId;
    private String abort;
    private String label;
    private String value;
    private String roleId;
    private int handle;
    private String tips;
    private String bookId;
    private String categoryName;
    private String categoryId;
    private String goodName;
    private int sales;
    private String goodId;
    private String remark;
    private double starLevel;
    private String specName;
    private String specValue;
    private String valueId;
    private String specSku;
    private String goodSku;
    private int status;
    private String addressId;
    private String userName;
    private String counties;
    private String county;
    private int goodNumber;
    private String goodNum;
    private String orderId;
    private String orderNum;
    private double allPrice;
    private int isAppraise;//0未评价 1已评价
    private String payTime;//支付时间
    private String deliveryTime;//发货时间
    private String cancelTime;//取消订单时间
    private String receiveTime;//收货时间
    private String expCode;
    private String expressNo;

    public String getExpCode() {
        return expCode;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public int getIsAppraise() {
        return isAppraise;
    }

    public double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(double allPrice) {
        this.allPrice = allPrice;
    }

    public int getGoodNumber() {
        return goodNumber;
    }

    public void setGoodNumber(int goodNumber) {
        this.goodNumber = goodNumber;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(String goodNum) {
        this.goodNum = goodNum;
    }

    public String getGoodSku() {
        return goodSku;
    }

    public void setGoodSku(String goodSku) {
        this.goodSku = goodSku;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCounty() {
        return county;
    }

    public String getCounties() {
        return counties;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddressId() {
        return addressId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSpecSku() {
        return specSku;
    }

    public void setSpecSku(String specSku) {
        this.specSku = specSku;
    }

    public String getValueId() {
        return valueId;
    }

    public String getSpecValue() {
        return specValue;
    }

    public String getSpecName() {
        return specName;
    }

    public double getStarLevel() {
        return starLevel;
    }

    public String getRemark() {
        return remark;
    }

    public String getGoodId() {
        return goodId;
    }

    public int getSales() {
        return sales;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTips() {
        return tips;
    }

    public int getHandle() {
        return handle;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getLabel() {
        return label;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getAbort() {
        return abort;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setcIsTrue(int cIsTrue) {
        this.cIsTrue = cIsTrue;
    }

    public int getfIsTrue() {
        return fIsTrue;
    }

    public String getVideo() {
        return video;
    }

    public int getInDay() {
        return inDay;
    }

    public String getBrithday() {
        return brithday;
    }

    public int getGoDay() {
        return goDay;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getcIsTrue() {
        return cIsTrue;
    }

    public int getpIsTrue() {
        return pIsTrue;
    }

    public String getChi1ldId() {
        return childId;
    }

    public String getSoId() {
        return soId;
    }

    public String getSoName() {
        return soName;
    }

    public String getComplainId() {
        return complainId;
    }

    public String getComplainName() {
        return complainName;
    }

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }

    public void setpIsTrue(int pIsTrue) {
        this.pIsTrue = pIsTrue;
    }

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

    public void setDiscuss(int discuss) {
        this.discuss = discuss;
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

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public int getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(int regionLevel) {
        this.regionLevel = regionLevel;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
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

    private List<DataBean> discussImgs;

    public List<DataBean> getDiscussImgs() {
        return discussImgs;
    }

    private List<DataBean> specValues;

    public List<DataBean> getSpecValues() {
        return specValues;
    }

    private List<DataBean> specList;

    public List<DataBean> getSpecList() {
        return specList;
    }

    private List<DataBean> welfareImgs;

    public List<DataBean> getWelfareImgs() {
        return welfareImgs;
    }

    private List<DataBean> informationImg;

    public List<DataBean> getInformationImg() {
        return informationImg;
    }

    private List<DataBean> goodSpuImgs;

    public List<DataBean> getGoodSpuImgs() {
        return goodSpuImgs;
    }

    private List<DataBean> list = new ArrayList<>();

    public void setList(List<DataBean> list) {
        this.list = list;
    }

    public List<DataBean> getList() {
        return list;
    }

    private DataBean userList;

    private DataBean infoDisList;

    public DataBean getUserList() {
        return userList;
    }

    public DataBean getInfoDisList() {
        return infoDisList;
    }

    private DataBean bInfoDisList;

    public DataBean getbInfoDisList() {
        return bInfoDisList;
    }

    private DataBean infoList;

    public DataBean getInfoList() {
        return infoList;
    }
}