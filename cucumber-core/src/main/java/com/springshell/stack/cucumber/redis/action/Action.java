package com.springshell.stack.cucumber.redis.action;

import com.alibaba.fastjson.JSONObject;
import com.springshell.stack.cucumber.WebSocketManager;

/**
 * 要做的事情
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public interface Action {

    String IDENTIFIER = "identifier";

    String MESSAGE = "message";

    String ACTION = "action";

    /**
     * 根据消息做自己的事情
     *
     * @param manager webSocket管理器
     * @param object  消息体转化的JSON
     */
    void doMessage(WebSocketManager manager, JSONObject object);
}
