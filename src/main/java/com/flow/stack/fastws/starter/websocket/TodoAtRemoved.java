package com.flow.stack.fastws.starter.websocket;

import java.util.List;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@FunctionalInterface
public interface TodoAtRemoved {
    /**
     * 在删除的时候额外要干什么
     *
     * @param webSockets webSockets
     */
    void todoWith(List<WebSocket> webSockets);
}
