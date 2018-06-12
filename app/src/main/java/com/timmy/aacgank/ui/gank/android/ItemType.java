package com.timmy.aacgank.ui.gank.android;

public class ItemType {

    private int type;
    private int layoutRes;

    public ItemType(int type, int layoutRes) {
        this.type = type;
        this.layoutRes = layoutRes;
    }

    public int getType() {
        return type;
    }

    public int getLayoutRes() {
        return layoutRes;
    }
}
