package com.flow.stack.fastws.starter.websocket;

import javax.websocket.Session;
import java.util.Date;

/**
 * WebSocket
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class WebSocket {

    /**
     * 代表一个连接
     */
    private Session session;

    /**
     * 唯一标识
     */
    private String identifier;

    /**
     * 最后心跳时间
     */
    private Date lastHeart;


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getLastHeart() {
        return lastHeart;
    }

    public void setLastHeart(Date lastHeart) {
        this.lastHeart = lastHeart;
    }
}
