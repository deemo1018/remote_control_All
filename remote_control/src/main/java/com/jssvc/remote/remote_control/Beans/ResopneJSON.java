package com.jssvc.remote.remote_control.Beans;

/**
 * @Author:lrb
 * @Date:2019/4/9_11:12
 * @Vserion:1.0
 */
public class ResopneJSON {
    public String errorCode;
    public String errorMsg;
    public Object data;

    public ResopneJSON(String errorCode, String errorMsg, Object data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }
}
