package org.hutcwp.gifts.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by hutcwp on 2017/7/30.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class Dynamic extends BmobObject {

    /**
     * publisher 发布者
     * publishTime 发布时间
     * content 发布的内容
     * imgs 发布的图表
     * comments 评论
     *
     */
    private String publisher;
    private String publishTime;
    private String content;
    private String imgs;
    private String comments;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
