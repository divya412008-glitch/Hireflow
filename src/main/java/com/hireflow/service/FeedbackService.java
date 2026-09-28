package com.hireflow.service;

import com.hireflow.entity.Feedback;
import com.hireflow.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    public List<Feedback> getFeedbackByInterviewId(Long interviewId) {
        return feedbackRepository.findByInterviewId(interviewId);
    }

    public List<Feedback> getFeedbackByInterviewerId(Long interviewerId) {
        return feedbackRepository.findByInterviewerId(interviewerId);
    }

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(Long id, Feedback feedbackDetails) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setInterview(feedbackDetails.getInterview());
        feedback.setInterviewer(feedbackDetails.getInterviewer());
        feedback.setRating(feedbackDetails.getRating());
        feedback.setComments(feedbackDetails.getComments());
        feedback.setRecommendation(feedbackDetails.getRecommendation());

        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
}