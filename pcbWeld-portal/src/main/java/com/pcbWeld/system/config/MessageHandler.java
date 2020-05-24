package com.pcbWeld.system.config;

import com.alibaba.fastjson.JSONObject;
import com.pcbWeld.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageHandler extends TextWebSocketHandler {
    private static Map<String,WebSocketSession> allClients =  new HashMap<>();
    /**
     *
     * @param session  当前发送消息的链接
     * @param message  当前发送的消息
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
       /* JSONObject jsonObject = JSONObject.parseObject(new String(message.asBytes()));
        String toUser= jsonObject.getString("orderNo"); //接收者
        String toMessage = jsonObject.getString("toMessage");//接收的内容
        String fromUser = (String)session.getAttributes().get("name");//发送者
        String content = "收到来自 "+fromUser +"的消息，内容是 " +toMessage;
        TextMessage textMessage = new TextMessage(content);
        sendMessage(toUser,textMessage);*/
    }

    public void sendMessage(String toUser,TextMessage toMessage){
        //获取多方的链接
        WebSocketSession webSocketSession  =allClients.get(toUser);
        if(webSocketSession!=null && webSocketSession.isOpen()){
            try {
                webSocketSession.sendMessage(toMessage);//发送消息啦
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  建立链接
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String name = (String) session.getAttributes().get("name");//获取拦截器中设置的name
        if(name!=null){
            allClients.put(name,session);
        }
    }

    /**
     * 关闭链接
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }


}
