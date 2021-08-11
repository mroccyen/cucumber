package com.springshell.stack.cucumber.heartbeat;

import com.springshell.stack.cucumber.WebSocket;

import java.util.List;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public interface WebSocketHeartBeatListener {

    boolean todoAtRemoved(List<WebSocket> webSockets);

    boolean todoAtChecking(WebSocket webSocket);
}
