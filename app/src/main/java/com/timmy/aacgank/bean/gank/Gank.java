package com.timmy.aacgank.bean.gank;

import java.io.Serializable;
import java.util.List;

public class Gank implements Serializable {

    //    "_id": "575640bf421aa9759750aee4",
//            "createdAt": "2016-06-07T11:34:23.596Z",
//            "desc": "\u9690\u85cf\u798f\u5229",
//            "publishedAt": "2016-06-07T11:43:18.947Z",
//            "source": "chrome",
//            "type": "\u798f\u5229",
//            "url": "http://ww2.sinaimg.cn/large/610dc034jw1f4mi70ns1bj20i20vedkh.jpg",
//            "used": true,
//            "who": "\u4ee3\u7801\u5bb6"

    private String _id;
    private String createdAt;//创建时间
    private String desc;//描述,一般作为标题使用
    private String publishedAt;//发布时间
    private String type;
    private String url;//项目跳转地址
    private boolean used;
    private String who;//作者
    private String source;//资源

    public List<String> images;//项目展示图片
    private int srcWidth;
    private int srcHeight;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getSrcWidth() {
        return srcWidth;
    }

    public void setSrcWidth(int srcWidth) {
        this.srcWidth = srcWidth;
    }

    public int getSrcHeight() {
        return srcHeight;
    }

    public void setSrcHeight(int srcHeight) {
        this.srcHeight = srcHeight;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "Gank{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
