package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Message;
import com.spring.sts.backend.exception.BodyIsNullException;
import com.spring.sts.backend.exception.ObjectNotFoundException;
import com.spring.sts.backend.repository.MessageRepository;
import com.spring.sts.backend.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        if (message.getContent() == null) {
            throw new BodyIsNullException("Message", "content");
        }
        Message savedMessage = messageRepository.save(message);
        log.info("IN MessageServiceImpl saveMessage - message: {} successfully saved", savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByFromUserAndToUser(String fromUser, String toUser) {
        List<Message> messages = messageRepository.findByFromUserAndToUser(fromUser, toUser);
        log.info("IN MessageServiceImpl getMessagesByFromUserOrToUser - return messages: {} ", messages);
        return messages;
    }

    @Override
    public String findToUserByFromUser(String fromUser) {
        String toUser = messageRepository.findToUserByFromUser(fromUser);
        log.info("IN MessageServiceImpl findToUserByFromUser - toUser: {} found by fromUser: {}", toUser, fromUser);
        return toUser;
    }

    @Override
    public List<Message> findModeratorInterlocutor(String moderator) {
        List<Message> interlocutors = messageRepository.findModeratorInterlocutor(moderator);
        log.info("IN MessageServiceImpl findModeratorInterlocutor - return messages: {} ", interlocutors);
        return interlocutors;
    }

}
