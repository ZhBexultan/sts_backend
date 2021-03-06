package com.spring.sts.backend.repository;

import com.spring.sts.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
