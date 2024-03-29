package com.springshell.stack.cucumber.redis;

import com.alibaba.fastjson.JSONObject;
import com.springshell.stack.cucumber.utils.ApplicationContextHolder;
import com.springshell.stack.cucumber.WebSocketManager;
import com.springshell.stack.cucumber.redis.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * redis消息订阅者
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class DefaultRedisReceiver implements RedisReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRedisReceiver.class);

    private CountDownLatch latch;

    public DefaultRedisReceiver(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * 此方法会被反射调用
     */
    @Override
    public void receiveMessage(String message) {

        LOGGER.info(message);

        JSONObject object = JSONObject.parseObject(message);
        if (!object.containsKey(Action.ACTION)) {
            return;
        }
        String actionName = object.getString(Action.ACTION);
        Action action = getAction(actionName);
        action.doMessage(getWebSocketManager(), object);

        latch.countDown();
    }

    private Action getAction(String actionName) {
        boolean containsBean = ApplicationContextHolder.getApplicationContext().containsBean(actionName);
        if (!containsBean) {
            throw new RuntimeException("容器中不存在处理这个请求 " + actionName + " 的Action，请确保正确注入了");
        }
        return ApplicationContextHolder.getBean(actionName, Action.class);
    }

    protected WebSocketManager getWebSocketManager() {
        boolean containsBean = ApplicationContextHolder.getApplicationContext().containsBean(WebSocketManager.WEBSOCKET_MANAGER_NAME);
        if (!containsBean) {
            throw new RuntimeException("容器中不存在WebSocketManager，请确保正确注入webSocketManger");
        }
        return ApplicationContextHolder.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME, WebSocketManager.class);
    }
}