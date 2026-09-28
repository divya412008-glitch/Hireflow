package com.hireflow.controller;

import com.hireflow.entity.Feedback;
import com.hireflow.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(
            @PathVariable Long id) {

        return feedbackService.getFeedbackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/interview/{interviewId}")
    public List<Feedback> getFeedbackByInterviewId(
            @PathVariable Long interviewId) {

        return feedbackService.getFeedbackByInterviewId(interviewId);
    }

    @GetMapping("/interviewer/{interviewerId}")
    public List<Feedback> getFeedbackByInterviewerId(
            @PathVariable Long interviewerId) {

        return feedbackService.getFeedbackByInterviewerId(interviewerId);
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(
            @RequestBody Feedback feedback) {

        return ResponseEntity.ok(
                feedbackService.createFeedback(feedback)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable Long id,
            @RequestBody Feedback feedback) {

        return ResponseEntity.ok(
                feedbackService.updateFeedback(id, feedback)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long id) {

        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}