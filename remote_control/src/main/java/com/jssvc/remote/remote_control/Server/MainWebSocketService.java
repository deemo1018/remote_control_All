package com.jssvc.remote.remote_control.Server;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.awt.*;
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
@ServerEndpoint(value = "/mainService")
@Component
public class MainWebSocketService {
    //静态 在线人数 线程安全
    private static int OnlineUser = 0;
    //静态  持续中的 websocket 心跳连接 线程安全
    private static CopyOnWriteArrayList<MainWebSocketService> webSockets = new CopyOnWriteArrayList<>();
    //当前的会话 线程不安全
    Session session;
    //当前会话 的ip
    String clientIp;
    Robot robot ;
    ByteArrayOutputStream baos;
    byte[] bytes;
    BufferedImage reimg;
    Toolkit tk = Toolkit.getDefaultToolkit();
    java.awt.Dimension dm = tk.getScreenSize();
    @OnOpen
    public void onOpen(Session session) {
        //保存当前会话 至 线程不安全
        this.session = session;
        //保存 当前连接到 安全线程 Set
        webSockets.add(this);
        //增加在线人数
        addOnlineUser();
        System.out.println("当前建立连接:"+session.getId());
    }

    @OnMessage
    public byte[] onMsg(String msg, Session session) throws AWTException, IOException {
//        System.out.println(msg);
        robot=new Robot();
		BufferedImage image = robot.createScreenCapture(new Rectangle(0,0,(int) dm.getWidth(),(int) dm.getHeight()));
//        System.out.println("屏幕尺寸"+dm.getHeight()+":"+dm.getWidth());
        BufferedImage tinyImg = Resize(image, (int) dm.getWidth(), (int) dm.getHeight());
        baos= new ByteArrayOutputStream();
		ImageIO.write(tinyImg, "png", baos);
        bytes= baos.toByteArray();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("data:image/jpeg;base64,"+encoder.encode(bytes));
        return bytes;
//		return "data:image/png;base64,"+encoder.encode(bytes);
    }


     public BufferedImage Resize(BufferedImage img,int newWe,int newHe) {

        int w = img.getWidth();
        int h = img.getHeight();
        reimg= new BufferedImage(newWe, newHe, img.getType());
        Graphics2D g = reimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newWe, newHe, 0, 0, w, h,null);
        g.dispose();
        return reimg;
    }

    @OnClose
    public void onClose() {
        //移除保持的连接
        webSockets.remove(this);
        //减少在线人数
        downOnlineUser();

        System.out.println(session.getId() + ",连接断开");
    }
    public void onError(Session session,Throwable error){
        System.err.println(session.getId()+"发生错误");
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

    /**
     * 增加在线人数
     */
    public static synchronized void addOnlineUser() {
        MainWebSocketService.OnlineUser++;
    }

    /**
     * 减少在线人数
     */
    public static synchronized void downOnlineUser() {
        MainWebSocketService.OnlineUser--;
    }

    /**
     * 获取在线人数
     *
     * @return
     */

    public static synchronized int getOnlineUser() {
        return OnlineUser;
    }

}
