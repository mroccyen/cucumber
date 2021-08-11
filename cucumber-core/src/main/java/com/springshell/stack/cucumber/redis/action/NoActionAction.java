package com.springshell.stack.cucumber.redis.action;

import com.alibaba.fastjson.JSONObject;
import com.springshell.stack.cucumber.WebSocketManager;

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
