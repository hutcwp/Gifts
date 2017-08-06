package org.hutcwp.gifts.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by hutcwp on 2017/8/6.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class QQMenu extends RelativeLayout {

    private ImageView childView1, childView2;

    private float childView1X, childView1Y, childView2X, childView2Y;
    private float centerX, centerY;
    private boolean hasClick = false;
    private int normal1, click1;
    private int normal2, click2;
    private OnMenuClickListener OnMenuClickListener;

    //ImageView的margin属性
    private final int MAGIN = 10;

    public QQMenu(@NonNull Context context) {
        super(context);
    }

    public QQMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QQMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        childView1 = new ImageView(getContext());
        childView2 = new ImageView(getContext());
        //设置间隔，防止滑动出边界外
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(MAGIN, MAGIN, MAGIN, 0);
        childView1.setLayoutParams(params);
        childView2.setLayoutParams(params);

        addView(childView1);
        addView(childView2);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        centerX = getHeight() / 5;
        centerY = getWidth() / 5;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        childView1 = (ImageView) getChildAt(0);
        childView2 = (ImageView) getChildAt(1);
        childView1X = childView1.getX();
        childView1Y = childView1.getY();
        childView2X = childView2.getX();
        childView2Y = childView2.getY();
    }

    public boolean isHasClick() {
        return hasClick;
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.OnMenuClickListener = onMenuClickListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void setImgages(@DrawableRes int normal1, @DrawableRes int click1) {
        this.normal1 = normal1;
        this.click1 = click1;

    }

    public void setImgages(@DrawableRes int normal1, @DrawableRes int click1, @DrawableRes int normal2, @DrawableRes int click2) {
        this.normal1 = normal1;
        this.click1 = click1;
        this.normal2 = normal2;
        this.click2 = click2;
        refreshDrawable();
    }

    /**
     * 跟新图标
     */
    private void refreshDrawable() {

        if (hasClick) {
            if (click1 != 0)
                childView1.setImageResource(click1);
            if (click2 != 0)
                childView2.setImageResource(click2);
        } else {
            if (normal1 != 0)
                childView1.setImageResource(normal1);
            if (normal2 != 0)
                childView2.setImageResource(normal2);
        }

    }

    /**
     * 是否点击（选中）
     * @param hasClick
     */
    public void setHasClick(boolean hasClick) {
        this.hasClick = hasClick;
        refreshDrawable();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                changeWhenMove(x, y);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                restorePosition();
                if (isContain(this, event.getRawX(), event.getRawY())) {
                    setHasClick(!hasClick);
                    if (OnMenuClickListener != null) {
                        OnMenuClickListener.onItemClick(this);
                    }
                }
                return true;
            }
        }
        return true;
    }

    /**
     * 是否包含？
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean isContain(View view, float x, float y) {
        int[] point = new int[2];
        view.getLocationOnScreen(point);
        return x >= point[0] && x <= (point[0] + view.getWidth()) && y >= point[1] && y <= (point[1] + view.getHeight());
    }

    /**
     * 移动的时候改变
     * @param x x坐标
     * @param y y坐标
     */
    private void changeWhenMove(float x, float y) {
        if (y + centerY < -12 * centerY) {
            y = -12 * centerY - centerY;
        } else if (y - centerY > 12 * centerY) {
            y = 12 * centerY + centerY;
        }
        if (x + centerX < -12 * centerX) {
            x = -12 * centerX - centerX;
        } else if (x - centerX > 12 * centerX) {
            x = 12 * centerX + centerX;
        }
        childView1.setX(childView1X + (x - centerX) / 23 + MAGIN);
        childView1.setY(childView1Y + (y - centerY) / 23 + MAGIN);
        if (childView2 != null) {
            childView2.setX(childView2X + (x - centerX) / 10 + MAGIN);
            childView2.setY(childView2Y + (y - centerY) / 10 + MAGIN);
        }

    }

    /**
     * 重置坐标位置
     */
    private void restorePosition() {

        childView1.setX(childView1X + MAGIN);
        childView1.setY(childView1Y + MAGIN);
        childView2.setX(childView2X + MAGIN);
        childView2.setY(childView2Y + MAGIN);
    }

    /**
     * 点击事件的接口
     *
     */
    public interface OnMenuClickListener {
        void onItemClick(View view);
    }

}
