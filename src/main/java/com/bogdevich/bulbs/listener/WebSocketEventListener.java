package com.bogdevich.bulbs.listener;

import com.bogdevich.bulbs.model.Message;
import com.bogdevich.bulbs.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

public class WebSocketEventListener {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(WebSocketEventListener.class);

    private static final String USERNAME_ARG = "username";

/*
    private final SimpMessageSendingOperations sendingOperations;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations sendingOperations) {
        this.sendingOperations = sendingOperations;
    }
*/

    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        LOGGER.info("Received a new web socket connection.");
    }

    public void handleWebSocketDissconnectListener(SessionDisconnectEvent event) {
        LOGGER.info("Connection is finished.");
/*
        StompHeaderAccessor headerAccessor =
                StompHeaderAccessor.wrap(event.getMessage());
        Optional
                .ofNullable(headerAccessor.getSessionAttributes().get(USERNAME_ARG))
                .ifPresent(username -> {
                    LOGGER.info("User is disconnected: " + username);
                    Message message = new Message();
                    message.setType(MessageType.LEAVE);
                    message.setSender((String) username);
                    sendingOperations.convertAndSend("topic/public", message);
                });
*/
    }
}
