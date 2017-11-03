package org.hutcwp.gifts.entity.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by hutcwp on 2017/11/2.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class Common extends BmobObject {

    private String dailyNotify ;
    private String signature ;
    private String phoneNumber ;
    private String QQNumber ;
    private String[] spannerImg ;
    private float versionCode;

    private String updateUrl ;

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public float getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(float versionCode) {
        this.versionCode = versionCode;
    }

    public String getDailyNotify() {
        return dailyNotify;
    }

    public void setDailyNotify(String dailyNotify) {
        this.dailyNotify = dailyNotify;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQQNumber() {
        return QQNumber;
    }

    public void setQQNumber(String QQNumber) {
        this.QQNumber = QQNumber;
    }

    public String[] getSpannerImg() {
        return spannerImg;
    }

    public void setSpannerImg(String[] spannerImg) {
        this.spannerImg = spannerImg;
    }
}
