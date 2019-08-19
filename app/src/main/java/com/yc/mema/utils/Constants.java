package com.yc.mema.utils;

import android.os.Environment;

import com.yc.mema.base.User;

/**
 * Created by yc on 2017/9/30.
 */

public class Constants {

   public static final int ITEMCOUNT = 10;
   public static final int pageSize = 20;

   public static final String BAI_DU = "V5h6NjfSgukQbu55EB0zRQKY83ghiNak";
   public static final String ShareID = "5d22bec94ca357c136000062";
   public static final String WX_APPID = "wxed3971fb41174390";
   public static final String WX_SECRER = "2851fc3b2a10291c7ef6719e4999acdd";
   public static final String QQ_APPID = "1106499610";
   public static final String QQ_SECRET = "mB4XpdFO6laSsV4j";
   public static final String WB_APPID = "761285940";
   public static final String WB_SECRET = "83c30696e0218437f471581f73b5985c";

   public static final String mainPath = Environment.getExternalStorageDirectory() + "/么马/";
   public static final String imgUrl = mainPath + "img/";
   public static final String videoUrl = mainPath + "video/";

   public static final String ZFB_PAY = "2018102061789029";

   //用户投诉原因
   public static final int REASONS_USER_COMPLAINTS = 1;
   //群投诉原因
   public static final int CAUSES_GROUP_COMPLAINTS = 2;
   //福利投诉原因
   public static final int CAUSES_WELFARE_COMPLAINTS  = 5;
   //评论投诉原因
   public static final int COMMENT_CAUSES_COMPLAINTS = 7;
   //资讯投诉原因
   public static final int REASONS_INFORMATION_COMPLAINTS = 8;
   //视频投诉原因
   public static final int CAUSES_VIDEO_COMPLAINTS = 9;


}
