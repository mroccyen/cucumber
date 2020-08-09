package com.flow.stack.fastws.starter.websocket.memory;

import com.flow.stack.fastws.starter.websocket.WebSocketEndpoint;
import com.flow.stack.fastws.starter.websocket.WebSocketManager;
import com.flow.stack.fastws.starter.websocket.utils.SpringContextHolder;
import com.flow.stack.fastws.starter.websocket.config.WebSocketConfig;
import com.flow.stack.fastws.starter.websocket.heartbeat.WebSocketHeartBeatChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 内存管理websocket配置
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@Import({WebSocketConfig.class, WebSocketEndpoint.class})
public class MemoryWebSocketConfig {
    /**
     * applicationContext全局保存器
     */
    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    @ConditionalOnMissingBean(name = WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public WebSocketManager webSocketManager() {
        return new MemWebSocketManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSocketHeartBeatChecker webSocketHeartBeatChecker() {
        return new WebSocketHeartBeatChecker();
    }
}