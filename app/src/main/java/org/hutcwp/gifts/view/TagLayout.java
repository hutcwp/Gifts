package org.hutcwp.gifts.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.hutcwp.gifts.R;

/**
 * Created by hutcwp on 2017/8/4.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class TagLayout extends RelativeLayout {
    View view;

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //加载布局
        view = LayoutInflater.from(context).inflate(R.layout.item_tag, this, true);
        String title = null;
        int img = 0;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagLayout);
        if (typedArray != null) {
            title = typedArray.getString(R.styleable.TagLayout_tag_title);
            img = typedArray.getResourceId(R.styleable.TagLayout_tag_img, R.drawable.app_logo);
            typedArray.recycle();
        }
        if (img != 0) {
            ((ImageView) findViewById(R.id.iv_icon)).setImageResource(img);
        }
        if (title != null) {
            ((TextView) findViewById(R.id.tv_title)).setText(title);
        }

    }


    //设置左边按钮的点击事件
    public void setClickListener(OnClickListener listener) {

        setListener(view, listener);

    }


    //给View设置OnClickListener
    private void setListener(View view, OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

}
