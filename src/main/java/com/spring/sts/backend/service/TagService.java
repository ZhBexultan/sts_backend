package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    Tag saveTag(Tag tag);

    List<Tag> getAllTags();

    Tag findById(Long id);

    void delete(Long id);

}
