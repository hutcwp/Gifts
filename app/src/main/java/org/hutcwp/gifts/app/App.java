package org.hutcwp.gifts.app;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by hutcwp on 2017/7/30.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, "a0330e29eb7f77c7ff07ec10b1e0c707");
    }
}
