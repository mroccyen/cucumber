package com.flow.stack.fastws.starter.websocket.endpoint;

import com.flow.stack.fastws.starter.websocket.WebSocket;
import com.flow.stack.fastws.starter.websocket.WebSocketManager;
import com.flow.stack.fastws.starter.websocket.utils.ApplicationContextHolder;
import com.flow.stack.fastws.starter.websocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.Date;

/**
 * 写自己的Endpoint类，继承自此类，添加@ServerEndpoint、@Component注解，
 * 然后在方法中添加@OnOpen、@OnMessage、@OnClose、@OnError即可，这些方法中可以调用父类方法
 * <p>
 * NOTE: Nginx反向代理要支持WebSocket，需要配置几个header，否则连接的时候就报404
 * proxy_http_version 1.1;
 * proxy_set_header Upgrade $http_upgrade;
 * proxy_set_header Connection "upgrade";
 * proxy_read_timeout 3600s; //这个时间不长的话就容易断开连接
 *
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public abstract class AbstractWebSocketEndpoint {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractWebSocketEndpoint.class);

    /**
     * 客户端标识
     */
    protected static final String IDENTIFIER = "identifier";

    /**
     * 客户端连接
     *
     * @param identifier 客户端身份识别号
     * @param session    session
     */
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

    /**
     * 关闭客户端连接
     *
     * @param identifier 客户端身份标识
     */
    protected void disconnect(String identifier) {
        getWebSocketManager().remove(identifier);
    }

    /**
     * 接收消息
     *
     * @param identifier 客户端身份标识
     * @param message    消息
     * @param session    session
     */
    protected void receive(String identifier, String message, Session session) {
        WebSocketManager webSocketManager = getWebSocketManager();
        //收到心跳消息
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

    /**
     * 获取当前webSocket管理器
     *
     * @return webSocket管理器
     */
    protected WebSocketManager getWebSocketManager() {
        return ApplicationContextHolder.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME, WebSocketManager.class);
    }
}
