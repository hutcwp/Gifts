package org.hutcwp.gifts.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.app.AppGlobal;

/**
 * Created by hutcwp on 2017/10/30.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class Utils {

    /**
     * 获取头像图片
     *
     */
    public static void getUserImgWithGlide(Context context  ,ImageView ivUserPhoto) {

        Glide.with(context)
                .load(AppGlobal.USER_IMAGE)
                .placeholder(R.drawable.default_user)
                .transform(new GlideCircleTransform(context))
                .into(ivUserPhoto);

    }
}
