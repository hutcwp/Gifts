package org.hutcwp.gifts.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.hutcwp.gifts.R;

/**
 * Created by hutcwp on 2017/7/29.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class TopTitle extends LinearLayout {


    private ImageView btnLeft;
    private ImageView btnRight;

    private TextView tvTitle;

    public TopTitle(Context context) {
        super(context);
    }

    public TopTitle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        //加载布局
        View view = LayoutInflater.from(context).inflate(R.layout.ly_title_common, this, true);

        btnLeft = (ImageView) view.findViewById(R.id.btn_left);
        btnRight = (ImageView) view.findViewById(R.id.btn_right);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);

    }

    //设置左边按钮的点击事件
    public void setBtnLeftOnClickListener(OnClickListener listener) {

        setListener(btnLeft, listener);
    }

    //设置右边按钮的点击事件
    public void serBtnRightOnClickListener(OnClickListener listener) {

        setListener(btnRight, listener);
    }

    //设置标题内容
    public void setTitle(String content) {
        if (tvTitle != null && content.length() < 12) {
            tvTitle.setText(content);
        } else {
            //Log提示
        }
    }


    //给View设置OnClickListener
    private void setListener(View view, OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    //给View设置Visiable
    void setVisiable(View view, int state) {
        if (view != null) {
            view.setVisibility(state);
        }
    }

    public void setLeftBtnVisibility(int state) {
        setVisiable(btnLeft, state);
    }

    public void setRightBtnVisibility(int state) {
        setVisiable(btnRight, state);
    }

    public void setTitleVisibility(int state) {
        setVisiable(tvTitle, state);
    }


}
