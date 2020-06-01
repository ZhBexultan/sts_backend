package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    Message saveMessage(Message message);

    List<Message> getMessagesByFromUserAndToUser(String fromUser, String toUser);

    String findToUserByFromUser(String fromUser);

    List<Message> findModeratorInterlocutor(String moderator);
}
