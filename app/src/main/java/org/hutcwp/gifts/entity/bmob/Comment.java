package org.hutcwp.gifts.entity.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by cwp on 2017❤8❤3.
 */

public class Comment extends BmobObject {

    private String content;
    private User user;//评论的用户，Pointer类型，一对一关系
    private Dynamic dynamic; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个微博

    private String  author;

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

}
