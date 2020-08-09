package com.flow.stack.fastws.starter.websocket.redis.action;

import org.springframework.context.annotation.Import;

/**
 * 将所有的Action配置进容器，通过名字找到
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@Import({SendMessageAction.class, BroadCastAction.class, RemoveAction.class, NoActionAction.class})
public class ActionConfig {
}