package com.springshell.stack.cucumber.memory;

import com.springshell.stack.cucumber.WebSocket;
import com.springshell.stack.cucumber.WebSocketManager;
import com.springshell.stack.cucumber.event.WebSocketCloseEvent;
import com.springshell.stack.cucumber.event.WebSocketConnectEvent;
import com.springshell.stack.cucumber.utils.ApplicationContextHolder;
import com.springshell.stack.cucumber.utils.WebSocketUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存型WebSocket管理器
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class MemWebSocketManager implements WebSocketManager {

    private final Map<String, WebSocket> connections = new ConcurrentHashMap<>(100);

    @Override
    public WebSocket get(String identifier) {
        return connections.get(identifier);
    }

    @Override
    public void put(String identifier, WebSocket webSocket) {
        connections.put(identifier, webSocket);
        //发送连接事件
        ApplicationContextHolder.getApplicationContext().publishEvent(new WebSocketConnectEvent(webSocket));
    }

    @Override
    public void remove(String identifier) {
        WebSocket removedWebSocket = connections.remove(identifier);
        //发送关闭事件
        if (null != removedWebSocket) {
            ApplicationContextHolder.getApplicationContext().publishEvent(new WebSocketCloseEvent(removedWebSocket));
        }
    }

    @Override
    public Map<String, WebSocket> localWebSocketMap() {
        return connections;
    }

    @Override
    public void sendMessage(String identifier, String message) {
        WebSocket webSocket = get(identifier);
        if (null == webSocket) {
            throw new RuntimeException("identifier 不存在");
        }

        WebSocketUtil.sendMessage(webSocket.getSession(), message);
    }

    @Override
    public void broadcast(String message) {
        localWebSocketMap().values().forEach(webSocket -> WebSocketUtil.sendMessage(webSocket.getSession(), message));
    }

    @Override
    public void onMessage(String identifier, String message) {

    }
}
