package com.pcbWeld.system.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 *  WebSocket握手  请求响应拦截器
 */
public class MessageInterceptor extends HttpSessionHandshakeInterceptor{
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("WebSocket握手之前");
        String url= request.getURI().toString();
        System.out.println(url);
        //给当前的链接配置名字
        attributes.put("name",url.substring(url.lastIndexOf("/")+1));
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.out.println("WebSocket握手之后");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
