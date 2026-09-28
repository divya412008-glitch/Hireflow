package com.hireflow.repository;

import com.hireflow.entity.PipelineStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PipelineStageRepository extends JpaRepository<PipelineStage, Long> {

    Optional<PipelineStage> findByName(String name);
}