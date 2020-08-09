package com.flow.stack.fastws.starter.websocket.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@Import(ApplicationContextConfig.class)
public class WebSocketConfig {
    /**
     * 链接：https://www.cnblogs.com/betterboyz/p/8669879.html
     * 首先要注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
     * todo 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，
     * 因为它将由容器自己提供和管理， 否则就会报重复的endpoint错误。
     */
    @ConditionalOnProperty(prefix = "flow.fastws.server.websocket.exporter", name = "enable", havingValue = "true")
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
