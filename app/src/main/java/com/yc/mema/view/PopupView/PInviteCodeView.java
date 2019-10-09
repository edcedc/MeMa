package com.yc.mema.view.PopupView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.WriterException;
import com.lxj.xpopup.core.CenterPopupView;
import com.yc.mema.R;
import com.yc.mema.base.User;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.utils.ImageUtils;
import com.yc.mema.weight.CircleImageView;
import com.yc.mema.weight.ZXingUtils;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/9
 * Time: 17:21
 */
public class PInviteCodeView extends CenterPopupView {

    private Context act;

    public PInviteCodeView(@NonNull Context context) {
        super(context);
        this.act = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.p_invite;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(view -> {
            dismiss();
        });
        CircleImageView iv_head = findViewById(R.id.iv_head);
        AppCompatTextView tv_name = findViewById(R.id.tv_name);
        AppCompatTextView tv_code = findViewById(R.id.tv_code);
        AppCompatTextView tv_content = findViewById(R.id.tv_content);
        AppCompatImageView iv_zking = findViewById(R.id.iv_zking);
        LinearLayout layout = findViewById(R.id.layout);

        JSONObject userObj = User.getInstance().getUserObj();
        GlideLoadingUtils.load(act, userObj.optString("headUrl"), iv_head, true);
        tv_name.setText(userObj.optString("nickName"));
        String inviteCode = userObj.optString("inviteCode");
        tv_code.setText(inviteCode);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#EC5B44"));
        SpannableString hText = new SpannableString("长按识别二维码，加入我们\n我在么马等你，不见不散");
        hText.setSpan(colorSpan, 15, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_content.setText(hText);

        iv_zking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_zking.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                try {
                    Bitmap bitmap = ZXingUtils.creatBarcode("http://47.106.179.240/share/#/?inviteCode=" + inviteCode, iv_zking.getWidth());
                    iv_zking.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.bt_submit).setOnClickListener(view -> {
            ImageUtils.viewSaveToImage((Activity) act, layout);
            ToastUtils.showShort("保存成功");
            dismiss();
        });

    }
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }

    //        @Override
//        protected int getMaxHeight() {
//            return 200;
//        }
//
    //返回0表示让宽度撑满window，或者你可以返回一个任意宽度
//        @Override
//        protected int getMaxWidth() {
//            return 1200;
//        }
}
