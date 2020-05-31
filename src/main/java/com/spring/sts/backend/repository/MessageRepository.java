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
    List<Message> findByFromUserOrToUser(String fromUser, String toUser);
    @Query(
            value = "SELECT to_id FROM sts_message WHERE from_id = ?1 ORDER BY send_time DESC LIMIT 1",
            nativeQuery = true
    )
    String findToUserByFromUser(String fromUser);
}
