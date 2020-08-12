package com.flow.stack.fastws.starter.websocket.redis.action;

import com.alibaba.fastjson.JSONObject;
import com.flow.stack.fastws.starter.websocket.utils.WebSocketUtil;
import com.flow.stack.fastws.starter.websocket.WebSocketManager;

/**
 * {
 * "action":"broadcast",
 * "message":"xxxxxxxxxxxxx"
 * }
 * 广播给所有的websocket发送消息 action
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class BroadCastAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(MESSAGE)) {
            return;
        }
        String message = object.getString(MESSAGE);
        //从本地取出所有的websocket发送消息
        manager.localWebSocketMap().values().forEach(
                webSocket -> WebSocketUtil.sendMessage(
                        webSocket.getSession(), message));
    }
}
