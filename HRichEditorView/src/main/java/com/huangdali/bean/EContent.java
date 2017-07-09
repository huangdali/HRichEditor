package com.huangdali.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 内容对象
 * Created by HDL on 2017/2/17.
 */

public class EContent implements Serializable {
    private String url;
    private String content;
    private String style;
    private String type;

    public EContent() {
    }

    public EContent(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public EContent(String url, String content, String style, String type) {
        this.url = url;
        this.content = content;
        this.style = style;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    @Override
    public String toString() {
        return "EContent{" +
                "url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", style='" + style + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getHtml() {
        String html = "";
        switch (type) {
            case ItemType.IMG:
                if (!TextUtils.isEmpty(content)) {
                    html = "<div style='" + style + "' >" + content + "</div><img src='" + url + "' />";
                } else {
                    html = "<img src='" + url + "' />";
                }
                html += "<br/>";
                break;
            case ItemType.VIDEO:
                if (!TextUtils.isEmpty(content)) {
                    html = "<div style='" + style + "' >" + content + "</div><video src='" + url + "' />";
                } else {
                    html = "<video src='" + url + "' />";
                }
                html += "<br/>";
                break;
            case ItemType.TXT:
                html = "<div style='" + style + "' >" + content + "</div>";
                html += "<br/>";
                break;
        }
        return html;
    }
}
