package com.springshell.stack.cucumber.heartbeat;

import com.springshell.stack.cucumber.WebSocket;

/**
 * 在检测webSocket的时候额外要干什么
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-12
 */
@FunctionalInterface
public interface TodoAtChecking {
    /**
     * 在检测webSocket的时候额外要干什么
     *
     * @param webSocket webSocket
     */
    void todoWith(WebSocket webSocket);
}
