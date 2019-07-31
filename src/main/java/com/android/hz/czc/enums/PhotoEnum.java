package com.android.hz.czc.enums;

import lombok.Data;

/**
 * Created by czc-pual on 2019/1/8.
 */
public enum PhotoEnum {
    HPOTO("width", "height");
    private String width;
    private String height;

    PhotoEnum(String width, String height) {
        this.width = width;
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public static void main(String[] args) {
        System.out.println(PhotoEnum.HPOTO.getWidth());
        System.out.println(PhotoEnum.HPOTO.getHeight());
    }
}
