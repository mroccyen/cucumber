package com.springshell.stack.cucumber.endpoint;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import static com.springshell.stack.cucumber.endpoint.AbstractWebSocketEndpoint.IDENTIFIER;

/**
 * Endpoint默认实现
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@ServerEndpoint(value = "${cucumber.webSocket.connect.path}" + "{" + IDENTIFIER + "}")
public class WebSocketEndpointImpl extends AbstractWebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("===> webSocket opened from sessionId " + session.getId() + " , identifier = " + identifier);
        connect(identifier, session);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("===> receive data：" + message + " from sessionId " + session.getId() + " , identifier = " + identifier);
        receive(identifier, message, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("===> webSocket closed from sessionId " + session.getId() + " , identifier = " + identifier);
        disconnect(identifier);
    }

    @OnError
    public void onError(Throwable t, @PathParam(IDENTIFIER) String identifier) {
        logger.info("===> occur exception, identifier = " + identifier);
        logger.error(t.getMessage(), t);
        disconnect(identifier);
    }
}
