package com.android.hz.czc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 大小
 */
public enum SizeEnum {

    BIG(1, "大号"),
    SMALL(0, "小号");
    @EnumValue
    private final Integer value;

    private final String descp;

    SizeEnum(Integer value, String descp) {
        this.value = value;
        this.descp = descp;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescp() {
        return descp;
    }
}
