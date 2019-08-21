package com.yc.mema.weight;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yc.mema.R;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/21
 * Time: 11:44
 */
public class PasswdEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    /**
     * 右侧Drawable引入
     */
    private Drawable mDrawableRight;
    /**
     * 判断当前密码打开状态，默认关闭状态
     */
    private boolean isOpen = false;

    // 构造方法 1->2->3
    public PasswdEditText(Context context) {
        this(context, null);
    }

    public PasswdEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PasswdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 初始化方法
    private void init() {
        // Drawable顺序左上右下，0123
        // 获取DrawRight内容
        mDrawableRight = getCompoundDrawables()[2];
        if (mDrawableRight == null) {
            // 未设置默认DrawableRight
            setmDrawableRight(isOpen);
        }
        setClearIconVisible(false);
    }

    // 初始化DrawableRight
    public void setmDrawableRight(boolean isOpen) {
        int drawableId;
        // 通过状态设置DrawableRight的样式
        if (!isOpen)
            drawableId = R.mipmap.icon_close_eye;
        else
            drawableId = R.mipmap.icon_open_eye;
        // 初始化DrawableRight
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableRight = getResources().getDrawable(drawableId, null);
        } else {
            mDrawableRight = getResources().getDrawable(drawableId);
        }
        // 设置Drawable大小和位置
        mDrawableRight.setBounds(0, 0, mDrawableRight.getIntrinsicWidth(), mDrawableRight.getIntrinsicHeight());
        // 将其添加到控件上
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawableRight, getCompoundDrawables()[3]);
    }

    // 触摸事件
    // 判断Drawable是否被点击，如果被点击执行点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                // 获取控件的DrawableRight
                Drawable drawable = getCompoundDrawables()[2];
                // 当按下的位置 在EditText的宽度 - 图标到控件右边的间距 - 图标的宽度 与 EditText的宽度 - 图标到控件右边的间距之间，算点击了图标，竖直方向不用考虑
                // getTotalPaddingRight获取右侧图标以及右侧Padding和
                // getPaddingRight获取右侧Padding值
                boolean isTouchRight = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (drawable != null && isTouchRight) {
                    // 记录状态
                    isOpen = !isOpen;
                    // 刷新DrawableRight
                    setmDrawableRight(isOpen);
                    // 执行点击事件-隐藏或显示
                    if (isOpen)
                        setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    else
                        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    // 设置光标位置
                    setSelection(getText().length());
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence text, int i, int i1, int i2) {
        setClearIconVisible(text.length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mDrawableRight : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }
}
