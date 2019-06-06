package com.jssvc.remote.remote_control.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author:lrb
 * @Date:2019/4/6_23:00
 * @Vserion:1.0
 */
//添加一个返回的配置
@Configuration
public class MySocketConfig {
    @Bean
    public ServerEndpointExporter webSocketExtension(){
        return  new ServerEndpointExporter();
    }
}
