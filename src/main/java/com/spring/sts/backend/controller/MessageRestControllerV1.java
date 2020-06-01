package com.spring.sts.backend.controller;

import com.spring.sts.backend.entity.InputMessage;
import com.spring.sts.backend.entity.Message;
import com.spring.sts.backend.entity.Role;
import com.spring.sts.backend.entity.User;
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
    public void sendMessage(@DestinationVariable String to,
                            InputMessage inMessage) {
        Message message = new Message();
        message.setFromUser(inMessage.getFromUser());
        message.setContent(inMessage.getContent());
        message.setSendTime(LocalDateTime.now());
        message.setToUser(to);
        messageService.saveMessage(message);
        Map<String, Object> result = new HashMap<>();
        result.put("messages", message);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to + "/" + inMessage.getFromUser(), result);
        User toUser = userService.findByUsername(to);
        if (toUser.getRole().equals(Role.ROLE_MODERATOR)) {
            simpMessagingTemplate.convertAndSend("/topic/interculators/" + to, messageService.findModeratorInterlocutor(to));
        }
    }

    @SubscribeMapping("/messages/{from}/{to}")
    public Map<String, Object> chatInit(@DestinationVariable String from, @DestinationVariable String to) {
        System.out.println("Subscribed with: " + from + " and " + to);
        Map<String, Object> result = new HashMap<>();
        String fromUser = "";
        if (from.equals("null")) {
            fromUser = RandomizerUser.generate() + "@soul.kz";
        } else {
            fromUser = from;
        }
        if (to.equals("null")) {
            String toUser = messageService.findToUserByFromUser(fromUser);
            if (toUser == null) {
                toUser = userService.getRandomModerator();
            }
            to = toUser;
        }
        List<Message> messages = messageService.getMessagesByFromUserAndToUser(from, to);
        result.put("fromUser", fromUser);
        result.put("toUser", to);
        result.put("messages", messages);
        return result;
    }

    @SubscribeMapping("/interculators/{moderator}")
    public List<Message> getInterculators (@DestinationVariable String moderator) {
        return messageService.findModeratorInterlocutor(moderator);
    }
}
