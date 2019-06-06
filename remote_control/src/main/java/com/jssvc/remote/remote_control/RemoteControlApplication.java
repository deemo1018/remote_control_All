package com.jssvc.remote.remote_control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.ResourceBundle;
import java.util.Scanner;


@SpringBootApplication
public class RemoteControlApplication {

    public static void main(String[] args) {
//        SpringApplication.run(RemoteControlApplication.class, args);
        String userName = (String) ResourceBundle.getBundle("user").getObject("userName");
        String userPwd = (String) ResourceBundle.getBundle("user").getObject("userPwd");
        System.out.println("用户名："+userName+" 密码 ："+userPwd);
        System.out.println("1:开始接受控制\r\n2:退出\r\n");
        Scanner scanner=new Scanner(System.in);
            if(scanner!=null){
                while(true)
                switch (scanner.nextInt()){
                    case 1:
                        SpringApplicationBuilder builder=new SpringApplicationBuilder(RemoteControlApplication.class);
                        builder.headless(false).web(WebApplicationType.SERVLET).run(args);
                        System.out.println("按下Ctrl+C结束程序");
                        System.out.println("当前监听端口：9527");
                        break;
                    case 2:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("请重新输入");
                        break;
                }
            }


    }
}
