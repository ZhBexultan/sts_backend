package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Feedback;
import com.spring.sts.backend.exception.BodyIsNullException;
import com.spring.sts.backend.exception.NoReturnDataException;
import com.spring.sts.backend.exception.ObjectNotFoundException;
import com.spring.sts.backend.repository.FeedbackRepository;
import com.spring.sts.backend.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        if (feedback.getComment() == null || feedback.getType() == null) {
            throw new BodyIsNullException("Feedback", "comment, type");
        }
        Feedback savedFeedback = feedbackRepository.save(feedback);
        log.info("IN FeedbackServiceImpl saveFeedback - feedback: {} successfully saved", savedFeedback);
        return savedFeedback;
    }

    @Override
    public void delete(Long id) {
        feedbackRepository.deleteById(id);
        log.info("IN FeedbackServiceImpl delete - feedback with id: {} successfully deleted", id);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        if (feedbacks.isEmpty()) {
            throw new NoReturnDataException("Feedbacks");
        }
        log.info("IN FeedbackServiceImpl getAllFeedbacks - {} feedbacks found", feedbacks.size());
        return feedbacks;
    }

    @Override
    public Feedback findById(Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Feedback", id));
        log.info("IN FeedbackServiceImpl findById - feedback: {} found by id: {}", feedback, id);
        return feedback;
    }

}
