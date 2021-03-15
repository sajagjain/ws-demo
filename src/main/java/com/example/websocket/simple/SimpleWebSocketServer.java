package com.example.websocket.simple;

import io.micronaut.websocket.CloseReason;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerWebSocket("/ws/simple/prices")
public class SimpleWebSocketServer {

    Logger LOG = LoggerFactory.getLogger(SimpleWebSocketServer.class);

    @OnOpen
    public Publisher<String> onOpen(WebSocketSession session){
        return session.send("Connected "+ session.getId());
    }

    @OnMessage
    public Publisher<String> onMessage(String message, WebSocketSession session){
        LOG.info("Recieved message: {} from session {}", message, session.getId());

        if(message.contentEquals("disconnect me")){
            LOG.info("Client Close Requested");
            session.close(CloseReason.NORMAL);
        }

        return session.send("Not supported : ("+message+")");
    }

    @OnClose
    public void onClose(WebSocketSession session){
        LOG.info("Session closed {}", session.getId());
    }
}
