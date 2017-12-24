package com.china.kuaiyou.activity.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.china.kuaiyou.R;

/**
 * @className :ClearEditText
 * @purpose: 自定义带删除的输入框
 * @author:
 * @data:2017/11/11 15:53
 */

public class ClearEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener,
        TextWatcher {

    private static final String TAG = "==" + ClearEditText.class.getSimpleName();
    //EditText右侧的删除按钮
    private Drawable mClearDrawable;
    private boolean hasFoucs;
    private inputListener il;
    private static final int limit = 11;//设置输入 最大字数限制
    private int cursor = 0;// 用来记录输入字符的时候光标的位置
    private int before_length;// 用来标注输入某一内容之前的编辑框中的内容的长度

    public void setIl(inputListener il) {
        this.il = il;
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,获取图片的顺序是左上右下（0,1,2,3,）
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(
                    R.mipmap.clean_);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    /* @说明：isInnerWidth, isInnerHeight为ture，触摸点在删除图标之内，则视为点击了删除图标
     * event.getX() 获取相对应自身左上角的X坐标
     * event.getY() 获取相对应自身左上角的Y坐标
     * getWidth() 获取控件的宽度
     * getHeight() 获取控件的高度
     * getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离
     * getPaddingRight() 获取删除图标右边缘到控件右边缘的距离
     * isInnerWidth:
     *  getWidth() - getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离
     *  getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
     * isInnerHeight:
     *  distance 删除图标顶部边缘到控件顶部边缘的距离
     *  distance + height 删除图标底部边缘到控件顶部边缘的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = getCompoundDrawables()[2].getBounds();
                int height = rect.height();
                int distance = (getHeight() - height) / 2;
                boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() - getPaddingRight());
                boolean isInnerHeight = y > distance && y < (distance + height);
                if (isInnerWidth && isInnerHeight) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，
     * 输入长度为零，隐藏删除图标，否则，显示删除图标
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        cursor = start;
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
        if (il != null)
            if (s.length() == 11) {
                il.isInput();
            } else
                il.noInput();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        before_length = s.length();
    }

    @Override
    public void afterTextChanged(Editable s) {
        int after_length = s.length();// 输入内容后编辑框所有内容的总长度
        // 如果字符添加后超过了限制的长度，那么就移除后面添加的那一部分，这个很关键
        if (after_length > 11) {
            // 比限制的最大数超出了多少字
            int d_value = after_length - limit;
            // 这时候从手机输入的字的个数
            int d_num = after_length - before_length;

            int st = cursor + (d_num - d_value);// 需要删除的超出部分的开始位置
            int en = cursor + d_num;// 需要删除的超出部分的末尾位置
            // 调用delete()方法将编辑框中超出部分的内容去掉
            Editable s_new = s.delete(st, en);
        }
    }


    public interface inputListener {
        void isInput();

        void noInput();
    }

}
