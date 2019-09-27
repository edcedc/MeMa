package com.yc.mema.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 21:30
 */
public class BusinessCassificationInEvent {

    public String classifyId;

    public String title;

    public BusinessCassificationInEvent(String classifyId, String title) {
        this.classifyId = classifyId;
        this.title = title;
    }
}
