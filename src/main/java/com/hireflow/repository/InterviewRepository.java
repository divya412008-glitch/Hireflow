package com.hireflow.repository;

import com.hireflow.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByStartTimeLessThanAndEndTimeGreaterThan(
            LocalDateTime endTime,
            LocalDateTime startTime
    );
}