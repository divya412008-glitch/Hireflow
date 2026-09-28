package com.hireflow.controller;

import com.hireflow.entity.PipelineStage;
import com.hireflow.service.PipelineStageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pipeline-stages")
public class PipelineStageController {

    private final PipelineStageService pipelineStageService;

    public PipelineStageController(PipelineStageService pipelineStageService) {
        this.pipelineStageService = pipelineStageService;
    }

    @GetMapping
    public List<PipelineStage> getAllStages() {
        return pipelineStageService.getAllStages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PipelineStage> getStageById(@PathVariable Long id) {
        return pipelineStageService.getStageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PipelineStage> getStageByName(@PathVariable String name) {
        return pipelineStageService.getStageByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PipelineStage> createStage(
            @RequestBody PipelineStage stage) {
        return ResponseEntity.ok(
                pipelineStageService.createStage(stage)
        );
    }
}