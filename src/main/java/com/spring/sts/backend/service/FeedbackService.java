package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {

    Feedback saveFeedback(Feedback feedback);

    List<Feedback> getAllFeedbacks();

    Feedback findById(Long id);

    void delete(Long id);

}
