package com.yc.mema.view.bottomFrg;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.yc.mema.R;
import com.yc.mema.base.BaseBottomSheetFrag;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/29
 * Time: 14:23
 */
public class CommentBottomFrg extends BaseBottomSheetFrag implements TextView.OnEditorActionListener {

    private AppCompatEditText etText;
    private int type = 1;

    @Override
    public int bindLayout() {
        return R.layout.f_comment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetEdit);

    }

    @Override
    public void initView(View view) {
        etText = view.findViewById(R.id.et_text);
        etText.setOnEditorActionListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close(false);
        etText.setText("");
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch(i){
            case EditorInfo.IME_ACTION_SEND:
                if (listener != null && type == 1){
                    listener.onFirstComment(textView.getText().toString());
                    dismiss();
                }else if (listener != null && type == 2){
                    listener.onSecondComment(position, infoId, discussId, textView.getText().toString(), pUserId);
                    dismiss();
                }
                break;
        }
        return true;
    }

    private onCommentListener listener;
    public void setOnCommentListener(onCommentListener listener){
        this.listener = listener;
    }

    private String infoId;
    private String discussId;
    private int position;
    private String pUserId;
    public void onSecondComment(int position, int type, String infoId, String discussId, String pUserId) {
        this.infoId = infoId;
        this.discussId = discussId;
        this.type = type;
        this.position = position;
        this.pUserId = pUserId;
    }

    public interface onCommentListener{
        void onFirstComment(String text);
        void onSecondComment(int position, String infoId, String discussId, String text, String pUserId);
    }

}
