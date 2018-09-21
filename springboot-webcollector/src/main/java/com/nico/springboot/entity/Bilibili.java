package com.nico.springboot.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author nico
 * @since 2018-09-20
 */
public class Bilibili implements Serializable {

    private static final long serialVersionUID = 1L;

    private String aid;
    private String title;
    private String url;
    private String content;
    private LocalDateTime time;
    private String pic;
    private String source;


    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Bilibili{" +
        ", aid=" + aid +
        ", title=" + title +
        ", url=" + url +
        ", content=" + content +
        ", time=" + time +
        ", pic=" + pic +
        ", source=" + source +
        "}";
    }
}
