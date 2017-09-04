package org.hutcwp.gifts.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cwp on 2017❤8❤10.
 */

public class TimeUtil {

    /**
     * 获取当前时间，以日期的形式返回
     * @return 日期的形式
     */
    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        return df.format(new Date());
    }
}
