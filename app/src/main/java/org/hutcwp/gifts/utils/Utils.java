package org.hutcwp.gifts.utils;

import android.content.Context;
import android.content.SharedPreferences;
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
     */
    public static void getUserImgWithGlide(Context context, ImageView ivUserPhoto) {

        Glide.with(context)
                .load(AppGlobal.USER_IMAGE)
                .placeholder(R.drawable.default_user)
                .transform(new GlideCircleTransform(context))
                .into(ivUserPhoto);

    }

    public static void saveSharedPrefrence(Context context,String tag,boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("file_shared", Context.MODE_APPEND).edit();//file_name即为文件名
//        editor.putString(msg, value);
        editor.putBoolean(tag, value);
//        editor.putInt("num", 100);
        editor.commit();//将数据持久化到存储介质中去
    }


    public static  boolean getSharedPrefrence(Context context,String tag){
        SharedPreferences preferences= context.getSharedPreferences("file_shared", Context.MODE_PRIVATE);
        //文件模式只有在创建的时候才使用
//        String str=preferences.getString("message", "没有找到");//第二个参数表示如果没有找到，则使用该默认值
        Boolean res =preferences.getBoolean(tag,false );

        return res;
    }
}
