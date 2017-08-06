package org.hutcwp.gifts.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.hutcwp.gifts.R;

/**
 * Created by hutcwp on 2017/8/6.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class BottomTiltle extends LinearLayout {


    QQMenu left;
    QQMenu right;
    QQMenu middle;

    OnItemClickListener listener;

    public BottomTiltle(Context context) {
        super(context);
    }

    public BottomTiltle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BottomTiltle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        //加载布局
        View view = LayoutInflater.from(context).inflate(R.layout.ly_bottom_navi, this, true);

        left = (QQMenu) findViewById(R.id.qqmenu_left);
        middle = (QQMenu) findViewById(R.id.qqmenu_middle);
        right = (QQMenu) findViewById(R.id.qqmenu_right);


        left.setImgages(R.drawable.skin_tab_icon_conversation_normal
                , R.drawable.skin_tab_icon_conversation_selected
                , R.drawable.rvq, R.drawable.rvr);

        middle.setImgages(R.drawable.skin_tab_icon_contact_normal
                , R.drawable.skin_tab_icon_contact_selected
                , R.drawable.sjw, R.drawable.sjx);

        right.setImgages(R.drawable.skin_tab_icon_now_normal
                , R.drawable.skin_tab_icon_now_selected
                , R.drawable.rvq, R.drawable.rvr);

        left.setOnMenuClickListener(new QQMenu.OnMenuClickListener() {
            @Override
            public void onItemClick(View view) {
                clickLeft();
            }
        });

        middle.setOnMenuClickListener(new QQMenu.OnMenuClickListener() {
            @Override
            public void onItemClick(View view) {
                clickMiddle();
            }
        });

        right.setOnMenuClickListener(new QQMenu.OnMenuClickListener() {
            @Override
            public void onItemClick(View view) {
                clickRight();
            }
        });


        //默认选择中间
        left.setHasClick(true);

    }

    private void clickRight() {
        left.setHasClick(false);
        middle.setHasClick(false);
        right.setHasClick(true);
        listener.rightItemClick();
    }

    private void clickMiddle() {
        left.setHasClick(false);
        middle.setHasClick(true);
        right.setHasClick(false);
        listener.middleItemClick();
    }

    private void clickLeft() {
        left.setHasClick(true);
        middle.setHasClick(false);
        right.setHasClick(false);
        listener.leftItemClick();
    }


    /**
     * 设置点击事件
     *
     * @param listener
     */
    public void setItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }

    public interface OnItemClickListener {
        void leftItemClick();

        void rightItemClick();

        void middleItemClick();
    }


}
