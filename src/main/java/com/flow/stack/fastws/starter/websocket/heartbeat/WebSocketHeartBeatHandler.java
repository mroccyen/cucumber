package com.flow.stack.fastws.starter.websocket.heartbeat;

import com.flow.stack.fastws.starter.websocket.WebSocket;

import java.util.List;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public interface WebSocketHeartBeatHandler {

    boolean todoAtRemoved(List<WebSocket> webSockets);

}
