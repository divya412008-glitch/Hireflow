package com.hireflow.repository;

import com.hireflow.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("""
            SELECT a.pipelineStage.name, COUNT(a)
            FROM Application a
            WHERE a.job.id = :jobId
            GROUP BY a.pipelineStage.name, a.pipelineStage.stageOrder
            ORDER BY a.pipelineStage.stageOrder
            """)
    List<Object[]> countApplicationsByStage(
            @Param("jobId") Long jobId
    );
}