package com.android.hz.czc.resultvue;


/**
 * @author WANGJIHONG
 * @description 响应码枚举，参考 HTTP状态码的语义
 * @date 2018年3月13日 下午8:35:00
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),

    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),

    /**
     * 接口不存在
     */
    NOT_FOUND(404),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }

}
