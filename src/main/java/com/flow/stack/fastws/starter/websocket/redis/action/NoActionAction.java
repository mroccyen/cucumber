package com.flow.stack.fastws.starter.websocket.redis.action;

import com.alibaba.fastjson.JSONObject;
import com.flow.stack.fastws.starter.websocket.WebSocketManager;

/**
 * do nothing action
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class NoActionAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        // do no thing
    }
}
