package com.timmy.aacgank.bean.my;

/**
 * Created by timmy1 on 16/11/8.
 */

public class MainModel {

    private int tag;
    private String desc;

    public MainModel(int tag,String desc){
        this.tag = tag;
        this.desc = desc;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
