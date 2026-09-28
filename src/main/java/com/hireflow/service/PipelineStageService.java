package com.hireflow.service;

import com.hireflow.entity.PipelineStage;
import com.hireflow.repository.PipelineStageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PipelineStageService {

    private final PipelineStageRepository pipelineStageRepository;

    public PipelineStageService(PipelineStageRepository pipelineStageRepository) {
        this.pipelineStageRepository = pipelineStageRepository;
    }

    public List<PipelineStage> getAllStages() {
        return pipelineStageRepository.findAll();
    }

    public Optional<PipelineStage> getStageById(Long id) {
        return pipelineStageRepository.findById(id);
    }

    public Optional<PipelineStage> getStageByName(String name) {
        return pipelineStageRepository.findByName(name);
    }

    public PipelineStage createStage(PipelineStage stage) {
        return pipelineStageRepository.save(stage);
    }
}