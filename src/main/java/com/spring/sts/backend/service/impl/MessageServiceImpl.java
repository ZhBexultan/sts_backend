package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Message;
import com.spring.sts.backend.exception.BodyIsNullException;
import com.spring.sts.backend.repository.MessageRepository;
import com.spring.sts.backend.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
