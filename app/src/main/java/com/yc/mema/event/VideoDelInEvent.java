package com.yc.mema.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/16
 * Time: 15:32
 */
public class VideoDelInEvent {

    public int position;

    public int getPosition() {
        return position;
    }

    public VideoDelInEvent(int position) {
        this.position = position;
    }
}
