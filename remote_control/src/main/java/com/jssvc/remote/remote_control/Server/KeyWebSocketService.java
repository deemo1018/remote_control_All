package com.jssvc.remote.remote_control.Server;

import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author:lrb
 * @Date:2019/4/6_23:01
 * @Vserion:1.0
 */
//访问的ws地址
@ServerEndpoint(value = "/keyService")
@Component
public class KeyWebSocketService {
    //静态  持续中的 websocket 心跳连接 线程安全
    private static CopyOnWriteArrayList<KeyWebSocketService> webSockets = new CopyOnWriteArrayList<>();
    //当前的会话 线程不安全
    Session session;
    //当前会话 的ip
    String clientIp;
    //    BASE64Encoder encoder=new BASE64Encoder();
    Robot robot;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    Toolkit tk = Toolkit.getDefaultToolkit();
    java.awt.Dimension dm = tk.getScreenSize();
    ByteArrayOutputStream baos;
    byte[] bytes;
    BufferedImage reimg;
    Map<String, Object> map;
    int Iheight;
    int Iwidth;
    int mx;
    int my;
    int scale;
    int code;

    @OnOpen
    public void onOpen(Session session) {
        //保存当前会话 至 线程不安全
        this.session = session;
        //保存 当前连接到 安全线程 Set
        webSockets.add(this);
        System.out.println("当前建立连接:" + session.getId());
    }

    @OnMessage
    public String onMsg(String msg, Session session) {
//        System.out.println(msg);
        map = new GsonJsonParser().parseMap(msg);
        int control = Integer.valueOf((String) map.get("errorCode"));
        switch (control) {
            //鼠标左键
            case 0:
                mx = Integer.valueOf(new GsonJsonParser().parseMap(map.get("data").toString()).get("x").toString());
                my = Integer.valueOf(new GsonJsonParser().parseMap(map.get("data").toString()).get("y").toString());
                try {
                    mousePress(mx, my, 0, Iwidth, Iheight);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                break;
            //鼠标滑轮
            case 1:
                scale = Integer.valueOf(new GsonJsonParser().parseMap(map.get("data").toString()).get("scale").toString());
                try {
                    mouseScroll(scale);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                break;
            //鼠标右键
            case 2:
                mx = Integer.valueOf(new GsonJsonParser().parseMap(map.get("data").toString()).get("x").toString());
                my = Integer.valueOf(new GsonJsonParser().parseMap(map.get("data").toString()).get("y").toString());
                try {
                    mousePress(mx, my, 2, Iwidth, Iheight);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                break;
            //键盘输入
            case 3:
                code = Integer.valueOf(new GsonJsonParser().parseMap(map.get("data").toString()).get("code").toString());
                try {
                    keyPressNo(code);
                } catch (AWTException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            //获取图片大小
            case 4:
                Iheight = Integer.valueOf(new GsonJsonParser().parseMap(map.get("data").toString()).get("height").toString());
                Iwidth = Integer.valueOf(new GsonJsonParser().parseMap(map.get("data").toString()).get("width").toString());
                break;
        }
        return "success";

    }

    @OnClose
    public void onClose() {
        //移除保持的连接
        webSockets.remove(this);
        System.out.println(session.getId() + ",连接断开");
    }

    public void onError(Session session, Throwable error) {
        System.err.println(session.getId() + "发生错误");
        error.printStackTrace();
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
//            e.printStackTrace();
        }
    }

    public void mousePress(int x, int y, int mouseButton, int Iwidth, int Iheight) throws AWTException {
        int Cx = x;
        int Cy = y;//这是测试酷酷酷o'l'j'k'l'j'k
//        System.out.println(Cx+": 屏幕尺寸"+Cy);
////        System.out.println(Cx+":"+Cy);
//        System.out.println(Iwidth  +":"+Iheight);
        robot.mouseMove(Cx, Cy);// 坐标
        switch (mouseButton) {
            case 0:
                robot.mousePress(InputEvent.BUTTON1_MASK);// 按下鼠标左键
                robot.mouseRelease(InputEvent.BUTTON1_MASK);// 松开鼠标左键
                return;
            case 2:
                robot.mousePress(InputEvent.BUTTON3_MASK);// 按下鼠标右键
                robot.mouseRelease(InputEvent.BUTTON3_MASK);// 松开鼠标右键
                return;
            case 1:
                robot.mousePress(InputEvent.BUTTON2_MASK);// 按下鼠标中键
                robot.mouseRelease(InputEvent.BUTTON2_MASK);// 松开鼠标中键
                return;
            default:
                return;
        }

    }

    public void mouseScroll(int scale) throws AWTException {
//        System.out.println("滚");
        robot.mouseWheel(scale);
    }

    public void keyPress(String str) throws AWTException, InterruptedException {
        char[] chars = str.toCharArray();
        int KeyNum;
        for (int i = 0; i < chars.length; i++) {
            KeyNum = Integer.valueOf(chars[i]).intValue();
            if (KeyNum >= 65 && KeyNum <= 90) ;
            else if (KeyNum >= 97 && KeyNum <= 122) {
                KeyNum -= 32;
            }
            {
                System.out.println(KeyNum);
                robot.keyPress(KeyNum); // 在屏幕上打字
                robot.keyRelease(KeyNum);
            }
            Thread.sleep(100);
        }
    }

    public void keyPressNo(int keyNum) throws AWTException, InterruptedException {
//        System.out.println(keyNum);
        switch (keyNum) {
            case 45:
                robot.keyPress(KeyEvent.VK_INSERT);
                robot.keyRelease(KeyEvent.VK_INSERT);
//                robot.keyPress();
                break;
            case 46:
                robot.keyPress(KeyEvent.VK_DELETE);
                robot.keyRelease(KeyEvent.VK_DELETE);
//                robot.keyPress();
                break;
            case 13:
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
//                robot.keyPress();
                break;
            case 219:
                robot.keyPress(91);
                robot.keyRelease(91);
                break;
            case 220:
                robot.keyPress(93);
                robot.keyRelease(93);
                break;
            case 221:
                robot.keyPress(92);
                robot.keyRelease(92);
                break;
            case 186:
                robot.keyPress(59);
                robot.keyRelease(59);
                break;
            case 187:
            case 189:
                break;
            case 188:
                robot.keyPress(44);
                robot.keyRelease(44);
                break;
            case 190:
                robot.keyPress(46);
                robot.keyRelease(46);
                break;
            case 191:
                robot.keyPress(47);
                robot.keyRelease(47);
                break;
            default:
                robot.keyPress(keyNum);
                robot.keyRelease(keyNum);
                break;
        }
    }

}
