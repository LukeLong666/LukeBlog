package com.luke.luke_blog.pojo;

import java.util.Date;

/**
 * 电影
 *
 * @author zhang
 * @date 2020/07/20
 */
public class Looper {

    private String id;
    private String title;
    private Integer order=1;
    private String state ="1";
    private String targetUrl;
    private String imageUrl;
    private Date createTime;
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Looper{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", order=" + order +
                ", state='" + state + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
