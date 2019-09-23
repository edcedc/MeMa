package com.yc.mema.view.bottomFrg;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseBottomSheetFrg;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/29
 * Time: 14:23
 */
public class CommentBottomFrg extends BaseBottomSheetFrg implements TextView.OnEditorActionListener {

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
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*if (editable.length() > 100){
                    showToast("字数过长可能无法显示");
                    return;
                }*/
            }
        });
        new Handler().postDelayed(() -> showInput(etText), 200);
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) act.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close(false);
        etText.setText("");
        type = 1;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch(i){
            case EditorInfo.IME_ACTION_SEND:
                String s = textView.getText().toString();
                if (StringUtils.isEmpty(s)){
                    dismiss();
                }else {
                    if (listener != null && type == 1){
                        listener.onFirstComment(s);
                        dismiss();
                    }else if (listener != null && type == 2){
                        listener.onSecondComment(position, infoId, discussId, s, pUserId);
                        dismiss();
                    }
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
