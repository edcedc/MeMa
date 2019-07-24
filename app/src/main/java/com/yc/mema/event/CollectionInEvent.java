package com.yc.mema.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 18:44
 */
public class CollectionInEvent {

    public boolean isCollection;//是否选择
    public boolean isReduction;//还原

    public CollectionInEvent(boolean isCollection, boolean isReduction) {
        this.isCollection = isCollection;
        this.isReduction = isReduction;
    }

    public CollectionInEvent(boolean isCollection) {
        this.isCollection = isCollection;
    }
}
