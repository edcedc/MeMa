package com.yc.mema.utils;

import com.yc.mema.callback.Convert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/9
 * Time: 15:13
 */
public class DigitalConversionUtils {

    public static String numeral(String text){
        switch (text){
            case "一":
                return "01";
            case "二":
                return "02";
            case "三":
                return "03";
            case "四":
                return "04";
            case "五":
                return "05";
            case "六":
                return "06";
            case "七":
                return "07";
            case "八":
                return "08";
            case "九":
                return "09";
            case "十":
                return "10";
            case "十一":
                return "11";
            case "十二":
                return "12";
            default:
                return "";
        }
    }

}
