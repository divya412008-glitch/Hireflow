package com.hireflow.repository;

import com.hireflow.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByInterviewId(Long interviewId);

    List<Feedback> findByInterviewerId(Long interviewerId);

    List<Feedback> findByRecommendation(String recommendation);

    List<Feedback> findByInterviewApplicationIdAndRecommendation(
            Long applicationId,
            String recommendation
    );
}