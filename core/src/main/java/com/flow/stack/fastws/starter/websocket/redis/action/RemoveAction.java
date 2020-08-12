package com.flow.stack.fastws.starter.websocket.redis.action;

import com.alibaba.fastjson.JSONObject;
import com.flow.stack.fastws.starter.websocket.WebSocket;
import com.flow.stack.fastws.starter.websocket.WebSocketManager;

import java.util.Map;

/**
 * {
 * "action":"remove",
 * "identifier":"xxx"
 * }
 * 给webSocket发送消息的action
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class RemoveAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(IDENTIFIER)) {
            return;
        }

        String identifier = object.getString(IDENTIFIER);

        Map<String, WebSocket> localWebSocketMap = manager.localWebSocketMap();
        if (localWebSocketMap.containsKey(identifier)) {
            localWebSocketMap.remove(identifier);
        }
    }
}
