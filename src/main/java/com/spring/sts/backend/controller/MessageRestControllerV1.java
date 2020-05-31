package com.spring.sts.backend.controller;

import com.spring.sts.backend.entity.InputMessage;
import com.spring.sts.backend.entity.Message;
import com.spring.sts.backend.service.MessageService;
import com.spring.sts.backend.service.RandomizerUser;
import com.spring.sts.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageRestControllerV1 {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @MessageMapping("/chat/{to}")
    public Message sendMessage(@DestinationVariable String to,
                               InputMessage inMessage) {
        Message message = new Message();
        message.setFromUser(inMessage.getFromUser());
        message.setContent(inMessage.getContent());
        message.setSendTime(LocalDateTime.now());
        message.setToUser(to);
        messageService.saveMessage(message);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        return message;
    }

    @SubscribeMapping("/topic/messages/{from}/{to}")
    public Map<String, Object> chatInit(@DestinationVariable String to,
                                  @DestinationVariable String from) {
        Map<String, Object> result = new HashMap<>();
        String fromUser = "";
        if (from == null) {
            fromUser = RandomizerUser.generate() + "@soul.kz";
        } else {
            fromUser = from;
        }
        if (to == null) {
            String toUser = messageService.findToUserByFromUser(fromUser);
            if (toUser == null) {
                toUser = userService.getRandomModerator();
            }
            to = toUser;
        }
        List<Message> messages = messageService.getMessagesByFromUserOrToUser(from, to);
        result.put("fromUser", fromUser);
        result.put("toUser", to);
        result.put("messages", messages);
        return result;
    }
}
