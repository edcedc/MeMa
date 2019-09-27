package com.yc.mema.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/27
 * Time: 16:18
 */
public class TentryInEvent {

    public int position;
    public int prePosition ;
    public int type ;

    public TentryInEvent(int position, int prePosition, int type) {
        this.position = position;
        this.prePosition = prePosition;
        this.type = type;
    }

    public TentryInEvent(int position, int prePosition) {
        this.position = position;
        this.prePosition = prePosition;
    }

}
