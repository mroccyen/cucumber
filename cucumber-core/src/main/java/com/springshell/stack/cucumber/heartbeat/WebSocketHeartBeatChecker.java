package com.springshell.stack.cucumber.heartbeat;

import com.springshell.stack.cucumber.WebSocket;
import com.springshell.stack.cucumber.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
public class WebSocketHeartBeatChecker {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHeartBeatChecker.class);

    public static WebSocketHeartBeatListener listener;

    /**
     * 定时检测 WebSocket 的心跳时间跟现在的间隔，超过设定的值说明失去了心跳，就去除他，并更新数据库
     * 基于每次 WebSocket 的心跳都更新其心跳时间
     *
     * @param webSocketManager 要检测的容器
     * @param timeSpan         检查到心跳更新时间大于这么毫秒就认为断开了（心跳时间）
     * @param errorTolerant    容忍没有心跳次数
     * @param todoAtRemoved    在删除的时候额外需要做的事情
     * @param todoAtChecking   在webSocket状态检测的时候要做的事情
     */
    public void check(WebSocketManager webSocketManager,
                      long timeSpan,
                      int errorTolerant,
                      TodoAtRemoved todoAtRemoved,
                      TodoAtChecking todoAtChecking) {
        final long timeSpans = timeSpan * errorTolerant;
        Map<String, WebSocket> socketMap = webSocketManager.localWebSocketMap();
        Date now = new Date();
        List<WebSocket> toRemoves = new LinkedList<>();
        socketMap.forEach((identifier, webSocket) -> {
            //状态检测的时候做点什么
            todoAtChecking.todoWith(webSocket);

            long interval = now.getTime() - webSocket.getLastHeart().getTime();
            if (interval >= timeSpans) {
                //如果session关闭
                if (!webSocket.getSession().isOpen()) {
                    //说明失去心跳了
                    logger.info("{} 失去心跳", identifier);
                    toRemoves.add(webSocket);
                }
            }
        });

        if (toRemoves.size() > 0) {
            for (WebSocket webSocket : toRemoves) {
                socketMap.remove(webSocket.getIdentifier());
            }

            //移除连接后额外做的事情，比如还有数据库操作
            todoAtRemoved.todoWith(toRemoves);
        }
    }
}
