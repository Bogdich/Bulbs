package com.bogdevich.bulbs.controller;

import com.bogdevich.bulbs.model.Bulb;
import com.bogdevich.bulbs.model.Message;
import com.bogdevich.bulbs.service.IBulbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BulbWebSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulbWebSocketController.class);

    private static final String USERNAME_ARG = "username";

    private final IBulbService bulbService;
    private final SimpMessagingTemplate template;

    @Autowired
    public BulbWebSocketController(
            IBulbService bulbService,
            SimpMessagingTemplate template
    ) {
        this.bulbService = bulbService;
        this.template = template;
    }
/*
    @MessageMapping("/chat.sendMe")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put(USERNAME_ARG, message.getSender());
        return message;
    }*/

    @SubscribeMapping("/topic/{bulbId}")
    public Bulb subscribe(@DestinationVariable Long bulbId) {
        LOGGER.info("New subscription on bulb id: " + bulbId);
        return bulbService
                .findOne(bulbId)
                .orElseThrow(() -> new RuntimeException("Unknown bulb identifier"));
    }

    @MessageMapping("/{bulbId}")
    @SendTo("app/topic/{bulbId}")
    public Bulb changeState(@Payload Bulb bulb, @DestinationVariable Long bulbId) {
        LOGGER.info("Changing state of bulb with id: " + bulbId);
        return bulbService
                .update(bulb, bulbId)
                .orElseThrow(() -> new RuntimeException("Unknown bulb identifier"));
    }

    @MessageExceptionHandler
    @SendToUser(value="/queue/errors")
    public String handleException(Throwable exception) {
        LOGGER.error("Exception is thrown: ", exception);
        return exception.getMessage();
    }
}
