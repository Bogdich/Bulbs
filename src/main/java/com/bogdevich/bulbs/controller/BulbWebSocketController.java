package com.bogdevich.bulbs.controller;

import com.bogdevich.bulbs.model.Bulb;
import com.bogdevich.bulbs.service.IBulbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class BulbWebSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulbWebSocketController.class);

    private final IBulbService bulbService;

    @Autowired
    public BulbWebSocketController(IBulbService bulbService) {
        this.bulbService = bulbService;
    }

    @MessageMapping("/{bulbId}")
    @SendTo("/topic/{bulbId}")
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
