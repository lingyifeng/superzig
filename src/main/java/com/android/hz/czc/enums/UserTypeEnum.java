package com.android.hz.czc.enums;

/**
 * Created by lingzong on 2019/7/29.
 */
public enum UserTypeEnum {
    ADMIN(1,"管理员"),NORMAL(2,"普通管理员");
    private int type;
    private String desc;

    UserTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
