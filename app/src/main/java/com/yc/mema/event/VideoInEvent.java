package com.yc.mema.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/25
 * Time: 12:08
 */
public class VideoInEvent {

    public int type;//0增加 1删除
    public String ids;

    public VideoInEvent(int type, String ids) {
        this.type = type;
        this.ids = ids;
    }
}
