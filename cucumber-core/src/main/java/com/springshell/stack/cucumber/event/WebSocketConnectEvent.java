package com.springshell.stack.cucumber.event;

import com.springshell.stack.cucumber.WebSocket;
import org.springframework.context.ApplicationEvent;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class WebSocketConnectEvent extends ApplicationEvent {

    public WebSocketConnectEvent(WebSocket webSocket) {
        super(webSocket);
    }

}
