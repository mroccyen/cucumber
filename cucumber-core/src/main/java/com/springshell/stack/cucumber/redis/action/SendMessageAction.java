package com.springshell.stack.cucumber.redis.action;

import com.alibaba.fastjson.JSONObject;
import com.springshell.stack.cucumber.utils.WebSocketUtil;
import com.springshell.stack.cucumber.WebSocket;
import com.springshell.stack.cucumber.WebSocketManager;

/**
 * {
 * "action":"sendMessage",
 * "identifier":"xxx",
 * "message":"xxxxxxxxxxx"
 * }
 * 给webSocket发送消息的action
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class SendMessageAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(IDENTIFIER)) {
            return;
        }
        if (!object.containsKey(MESSAGE)) {
            return;
        }

        String identifier = object.getString(IDENTIFIER);

        WebSocket webSocket = manager.get(identifier);
        if (null == webSocket) {
            return;
        }
        WebSocketUtil.sendMessage(webSocket.getSession(), object.getString(MESSAGE));
    }
}
