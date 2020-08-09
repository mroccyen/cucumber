package com.flow.stack.fastws.starter.websocket.config;

import com.flow.stack.fastws.starter.websocket.WebSocketManager;
import com.flow.stack.fastws.starter.websocket.heartbeat.WebSocketHeartBeatChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 只需要配置一下三个选项即可开启心跳监测
 * flow.fastws.webSocket.heartCheck.enabled=true
 * flow.fastws.webSocket.heartCheck.timeSpan=1000
 * flow.fastws.webSocket.heartCheck.errorToleration=30
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@Configuration
@ConditionalOnProperty(prefix = "flow.fastws.webSocket.heartCheck", name = "enabled", havingValue = "true")
public class WebSocketHeartBeatConfig {

    @Value("${flow.fastws.webSocket.heartCheck.timeSpan:10000}")
    private long timeSpan;

    @Value("${flow.fastws.webSocket.heartCheck.errorToleration:30}")
    private int errorToleration;

    private final WebSocketManager webSocketManager;

    private final WebSocketHeartBeatChecker webSocketHeartBeatChecker;

    @Autowired
    public WebSocketHeartBeatConfig(WebSocketManager webSocketManager,
                                    WebSocketHeartBeatChecker webSocketHeartBeatChecker) {
        this.webSocketManager = webSocketManager;
        this.webSocketHeartBeatChecker = webSocketHeartBeatChecker;
    }

    /**
     * 定时检测 WebSocket 的心跳
     */
    @Scheduled(cron = "${flow.fastws.webSocket.heartCheck.trigger}")
    public void webSocketHeartCheckJob() {
        webSocketHeartBeatChecker.check(webSocketManager, timeSpan, errorToleration, (webSockets) -> {
            if (WebSocketHeartBeatChecker.heartBeatHandler != null) {
                //失去心跳后执行额外的操作
                WebSocketHeartBeatChecker.heartBeatHandler.todoAtRemoved(webSockets);
            }
        });
    }
}
