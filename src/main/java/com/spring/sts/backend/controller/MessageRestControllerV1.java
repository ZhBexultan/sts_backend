package com.spring.sts.backend.controller;

import com.spring.sts.backend.entity.Message;
import com.spring.sts.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
public class MessageRestControllerV1 {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    public void sendMessage(@Payload Message message,
                            Principal user) {
        message.setSendDate(LocalDateTime.now());
        messageService.saveMessage(message);
        simpMessagingTemplate.convertAndSendToUser(message.getToUser().getUsername(),
                "/user/queue/specific-user",
                message);
    }

}
