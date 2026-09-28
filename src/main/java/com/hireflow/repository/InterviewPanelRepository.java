package com.hireflow.repository;

import com.hireflow.entity.InterviewPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InterviewPanelRepository extends JpaRepository<InterviewPanel, Long> {

    List<InterviewPanel> findByInterviewId(Long interviewId);

    List<InterviewPanel> findByInterviewerId(Long interviewerId);

    @Query("""
            SELECT p
            FROM InterviewPanel p
            JOIN p.interview i
            WHERE p.interviewer.id = :interviewerId
              AND i.startTime < :endTime
              AND i.endTime > :startTime
            """)
    List<InterviewPanel> findConflictingAssignments(
            @Param("interviewerId") Long interviewerId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}