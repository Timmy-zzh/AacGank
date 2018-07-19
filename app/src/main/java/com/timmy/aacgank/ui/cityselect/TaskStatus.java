package com.timmy.aacgank.ui.cityselect;

/**
 * 需求:TextView根据不同的状态展示不同文本和颜色值
 */
public enum TaskStatus {

    STATUS_UN_START(0, "未开始", "#453214"),
    STATUS_PROGRESS(1, "进行中", "#654697"),
    STATUS_COMPLETED(2, "已完成", "#975439");

    int mStatus;
    String mText;
    String mColor;

    TaskStatus(int status, String text, String color) {
        this.mStatus = status;
        this.mText = text;
        this.mColor = color;
    }

    //根据后台返回的状态值,获取对应的状态枚举值
    public static TaskStatus getTaskStatus(int status) {
        for (TaskStatus taskStatus : values()) {
            if (taskStatus.mStatus == status) {
                return taskStatus;
            }
        }
        return STATUS_UN_START;
    }
}
