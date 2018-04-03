package com.bogdevich.bulbs.listener;

import com.bogdevich.bulbs.service.IBulbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class StompSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StompSubscribeEventListener.class);

    private final SimpMessageSendingOperations operations;
    private final IBulbService bulbService;

    @Autowired
    public StompSubscribeEventListener(
            SimpMessageSendingOperations operations,
            IBulbService bulbService) {
        this.operations = operations;
        this.bulbService = bulbService;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        LOGGER.info(headerAccessor.toString());

        String destination = headerAccessor.getDestination();
        Assert.notNull(destination, "Message destination is null.");
        bulbService
                .findOne(parseId(destination))
                .ifPresent(bulb -> operations.convertAndSend(destination, bulb));
    }

    private Long parseId(String destination) {
        Long id = -1L;
        if (destination.matches(".*/[0-9]+")) {
            int lastSlashIndex = destination.lastIndexOf('/');
            id = Long.parseLong(destination.substring(++lastSlashIndex));
        }
        LOGGER.info("Session subscribe event - bulb id: " + id);
        return id;
    }
}
