package com.flow.stack.fastws.starter.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@ServerEndpoint(value = "/websocket/connect/" + "{identifier}")
public class WebSocketEndpoint extends AbstractWebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("*** WebSocket opened from sessionId " + session.getId() + " , identifier = " + identifier);
        connect(identifier, session);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("接收到的数据为：" + message + " from sessionId " + session.getId() + " , identifier = " + identifier);
        receiveMessage(identifier, message, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("*** WebSocket closed from sessionId " + session.getId() + " , identifier = " + identifier);
        disconnect(identifier);
    }

    @OnError
    public void onError(Throwable t, @PathParam(IDENTIFIER) String identifier) {
        logger.info("发生异常：, identifier = " + identifier);
        logger.error(t.getMessage(), t);
        disconnect(identifier);
    }
}
