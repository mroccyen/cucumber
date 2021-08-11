package com.springshell.stack.cucumber.redis;

/**
 * redis 接收器接口,主要目的是固定接口名字
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public interface RedisReceiver {

    String RECEIVER_METHOD_NAME = "receive";

    /**
     * 回调方法
     *
     * @param message 接收到的消息
     */
    void receiveMessage(String message);
}
