package com.android.hz.czc.resultvue;

/**
 * @author WANGJIHONG
 * @description 响应结果生成工厂类
 * @date 2018年3月13日 下午8:36:58
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class ResultFactory {

    public static Result buildSuccessResult(Object data) {
        return buidResult(ResultCode.SUCCESS, "成功", data);
    }

    public static Result buildFailResult(String message) {
        return buidResult(ResultCode.FAIL, message, null);
    }

    public static Result buidResult(ResultCode resultCode, String message, Object data) {
        return buidResult(resultCode.code, message, data);
    }

    public static Result buidResult(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }
}

