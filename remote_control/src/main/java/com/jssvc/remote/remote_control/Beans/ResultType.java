package com.jssvc.remote.remote_control.Beans;

/**
 * @Author:lrb
 * @Date:2019/4/9_11:09
 * @Vserion:1.0
 */
public enum ResultType {
    SUCCESS("0","成功"),
    ERROR_PARAM("-1","参数错误"),
    Failed("-2","系统错误");

    public String errorCode;
    public String errorMsg;
    ResultType(String errorCode, String errorMsg) {
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }
}
