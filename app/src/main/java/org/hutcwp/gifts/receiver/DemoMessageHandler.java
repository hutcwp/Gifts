package org.hutcwp.gifts.receiver;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by hutcwp on 2017/8/4.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class DemoMessageHandler extends BmobIMMessageHandler {

    @Override
    public void onMessageReceive(final MessageEvent event) {
        //在线消息
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
    }
}