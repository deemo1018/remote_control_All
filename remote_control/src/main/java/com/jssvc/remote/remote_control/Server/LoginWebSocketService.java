package com.jssvc.remote.remote_control.Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jssvc.remote.remote_control.Beans.ResopneJSON;
import com.jssvc.remote.remote_control.Beans.ResultType;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author:lrb
 * @Date:2019/4/6_23:01
 * @Vserion:1.0
 */
//访问的ws地址
@ServerEndpoint(value = "/loginService")
@Component
public class LoginWebSocketService {
    //静态  持续中的 websocket 心跳连接 线程安全
    private static CopyOnWriteArrayList<LoginWebSocketService> webSockets = new CopyOnWriteArrayList<>();
    //当前的会话 线程不安全
    Session session;

    @OnOpen
    public void onOpen(Session session) {
        //保存当前会话 至 线程不安全
        this.session = session;
        //保存 当前连接到 安全线程 Set
        webSockets.add(this);
        System.out.println("当前建立连接:" + session.getId());
    }

    @OnMessage
    public void onMsg(String msg, Session session) {
        Map<String, Object> result = new GsonJsonParser().parseMap(msg);
        Gson gson = new GsonBuilder().create();
        Map<String, Object> userInfo = new GsonJsonParser().parseMap(result.get("data").toString());
        String userName = (String) ResourceBundle.getBundle("user").getObject("userName");
        String userPwd = (String) ResourceBundle.getBundle("user").getObject("userPwd");
        System.out.println(userInfo);
        if (userInfo.get("userName").equals(userName)) {
            if (userInfo.get("userPwd").equals(userPwd)) {
                this.sendMsg(gson.toJson(new ResopneJSON(ResultType.SUCCESS.errorCode, ResultType.SUCCESS.errorMsg, userInfo.get("userName"))));
            }else {
                this.sendMsg(gson.toJson(new ResopneJSON(ResultType.ERROR_PARAM.errorCode, ResultType.ERROR_PARAM.errorMsg, null)));
            }
        }
        else {
            this.sendMsg(gson.toJson(new ResopneJSON(ResultType.ERROR_PARAM.errorCode, ResultType.ERROR_PARAM.errorMsg, null)));
        }

        //同步消息
//        for (LoginWebSocketService e : webSockets) {
//            e.sendMsg(msg);
//        }
    }

    @OnClose
    public void onClose() {
        //移除保持的连接
        webSockets.remove(this);
        //减少在线人数
        System.out.println(session.getId() + ",连接断开");
    }

    public void onError(Session session, Throwable error) {
        System.err.println(session.getId() + "发生错误"+error);
//        error.printStackTrace();

    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
//            e.toString();
        }
    }
}
