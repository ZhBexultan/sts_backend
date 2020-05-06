package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Tag;
import com.spring.sts.backend.repository.TagRepository;
import com.spring.sts.backend.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        Tag savedTag = tagRepository.save(tag);
        log.info("IN TagServiceImpl saveTag - tag: {} successfully saved", savedTag);
        return savedTag;
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        log.info("IN TagServiceImpl getAllTags - {} tags found", tags.size());
        return tags;
    }

    @Override
    public Tag findById(Long id) {
        Tag tag = tagRepository.findById(id).orElse(null);
        if (tag == null) {
            log.warn("IN TagServiceImpl findById - no tag found by id: {}", id);
            return null;
        }
        log.info("IN TagServiceImpl findById - tag: {} found by id: {}", tag, id);
        return tag;
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
        log.info("IN TagServiceImpl delete - tag with id: {} successfully deleted", id);
    }
}
