package com.springshell.stack.cucumber.config.memory;

import com.springshell.stack.cucumber.WebSocketManager;
import com.springshell.stack.cucumber.config.WebSocketConfig;
import com.springshell.stack.cucumber.endpoint.WebSocketEndpointImpl;
import com.springshell.stack.cucumber.memory.MemWebSocketManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 内存管理webSocket配置
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@Import({WebSocketConfig.class, WebSocketEndpointImpl.class})
public class MemoryWebSocketConfig {

    @Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    @ConditionalOnMissingBean(name = WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public WebSocketManager webSocketManager() {
        return new MemWebSocketManager();
    }

}