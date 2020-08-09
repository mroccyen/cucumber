package com.flow.stack.fastws.starter.websocket;

import com.flow.stack.fastws.starter.websocket.utils.SpringContextHolder;
import com.flow.stack.fastws.starter.websocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.Date;

/**
 * NOTE: Nginx反向代理要支持WebSocket，需要配置几个header，否则连接的时候就报404
 * proxy_http_version 1.1;
 * proxy_set_header Upgrade $http_upgrade;
 * proxy_set_header Connection "upgrade";
 * proxy_read_timeout 3600s; //这个时间不长的话就容易断开连接
 */
/*@Component
@ServerEndpoint(value ="/websocket/connect/{identifier}")*/

/**
 * 写自己的Endpoint类，继承自此类，添加@ServerEndpoint、@Component注解，
 * 然后在方法中添加@OnOpen、@OnMessage、@OnClose、@OnError即可，这些方法中可以调用父类方法
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public abstract class AbstractWebSocketEndpoint {

    /**
     * 路径标识：目前使用token来代表
     */
    protected static final String IDENTIFIER = "identifier";

    protected static final Logger logger = LoggerFactory.getLogger(AbstractWebSocketEndpoint.class);

    protected void connect(String identifier, Session session) {
        try {

            if (null == identifier || "".equals(identifier)) {
                return;
            }

            WebSocketManager websocketManager = getWebSocketManager();

            WebSocket webSocket = new WebSocket();
            webSocket.setIdentifier(identifier);
            webSocket.setSession(session);
            webSocket.setLastHeart(new Date());
            //像刷新这种，id一样，session不一样，后面的覆盖前面的
            websocketManager.put(identifier, webSocket);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void disconnect(String identifier) {
        getWebSocketManager().remove(identifier);
    }

    protected void receiveMessage(String identifier, String message, Session session) {
        WebSocketManager webSocketManager = getWebSocketManager();
        //心跳监测
        if (webSocketManager.isPing(identifier, message)) {
            String pong = webSocketManager.pong(identifier, message);
            WebSocketUtil.sendMessage(session, pong);
            WebSocket webSocket = webSocketManager.get(identifier);
            //更新心跳时间
            if (null != webSocket) {
                webSocket.setLastHeart(new Date());
            }
            return;
        }
        //收到其他消息的时候
        webSocketManager.onMessage(identifier, message);
    }

    protected WebSocketManager getWebSocketManager() {
        return SpringContextHolder.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME, WebSocketManager.class);
    }
}
