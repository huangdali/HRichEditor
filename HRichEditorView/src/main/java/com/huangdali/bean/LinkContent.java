package com.huangdali.bean;

import java.io.Serializable;

/**
 * 链接内容
 * Created by HDL on 2017/7/6.
 */

public class LinkContent implements Serializable {
    /**
     * 链接标题
     */
    private String title;
    /**
     * 链接
     */
    private String link;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "LinkContent{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
