package com.hireflow.config;

import com.hireflow.entity.PipelineStage;
import com.hireflow.repository.PipelineStageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipelineStageInitializer {

    @Bean
    CommandLineRunner initializePipelineStages(
            PipelineStageRepository pipelineStageRepository) {

        return args -> {

            createStageIfNotExists(
                    pipelineStageRepository,
                    "SCREENING",
                    1
            );

            createStageIfNotExists(
                    pipelineStageRepository,
                    "INTERVIEW",
                    2
            );

            createStageIfNotExists(
                    pipelineStageRepository,
                    "OFFER",
                    3
            );

            createStageIfNotExists(
                    pipelineStageRepository,
                    "HIRED",
                    4
            );

            createStageIfNotExists(
                    pipelineStageRepository,
                    "REJECTED",
                    5
            );
        };
    }

    private void createStageIfNotExists(
            PipelineStageRepository repository,
            String name,
            Integer stageOrder) {

        if (repository.findByName(name).isEmpty()) {

            PipelineStage stage = new PipelineStage();
            stage.setName(name);
            stage.setStageOrder(stageOrder);

            repository.save(stage);
        }
    }
}