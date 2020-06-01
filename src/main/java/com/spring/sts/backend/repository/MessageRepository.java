package com.spring.sts.backend.repository;

import com.spring.sts.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(
            value = "SELECT * FROM sts_message WHERE from_id = ?1 AND to_id = ?2 OR from_id = ?2 AND to_id = ?1 ORDER BY send_time",
            nativeQuery = true
    )
    List<Message> findByFromUserAndToUser(String fromUser, String toUser);

    @Query(
            value = "SELECT to_id FROM sts_message WHERE from_id = ?1 ORDER BY send_time DESC LIMIT 1",
            nativeQuery = true
    )
    String findToUserByFromUser(String fromUser);

    @Query(
            value = "SELECT * FROM sts_message \n" +
                    "WHERE (from_id, send_time) IN \n" +
                    "(SELECT from_id, MAX(send_time) FROM sts_message WHERE from_id IN \n" +
                    " (SELECT DISTINCT from_id FROM sts_message WHERE to_id = ?1) GROUP BY from_id)",
            nativeQuery = true
    )
    List<Message> findModeratorInterlocutor (String moderator);
}
