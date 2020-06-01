package com.spring.sts.backend.repository;

import com.spring.sts.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query(
            value = "SELECT username FROM sts_user WHERE role = 'ROLE_MODERATOR' ORDER BY random() LIMIT 1",
            nativeQuery = true
    )
    String findRandomModerator();
}
