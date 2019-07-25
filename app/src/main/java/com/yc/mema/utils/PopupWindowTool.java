package com.yc.mema.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.yc.mema.R;
import com.yc.mema.weight.WPopupWindow;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PopupWindowTool {

    public static final int clear = 1; //清除缓存


    public static void showDialog(final Context act, final int type, final DialogListener listener){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_dialog, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        TextView tvTitle = wh.findViewById(R.id.tv_title);
        TextView btCancel = wh.findViewById(R.id.bt_cancel);
        TextView btSubmit = wh.findViewById(R.id.bt_submit);
        View view = wh.findViewById(R.id.view);
        wh.findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        switch (type){
            case clear:
                tvTitle.setText("确定清除缓存吗？");
                break;
        }
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick();
                    popupWindow.dismiss();
                }
            }
        });
    }

    public interface DialogListener{
        void onClick();
    }


}
