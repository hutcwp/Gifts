package org.hutcwp.gifts.entity.bmob;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

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
     */
    private User publisher;
    private String publishTime;
    private String content;
    private List<BmobFile> imgs;
    private Integer commentCount;

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
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

    public List<BmobFile> getImgs() {
        return imgs;
    }

    public void setImgs(List<BmobFile> imgs) {
        this.imgs = imgs;
    }
}
