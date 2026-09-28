package com.hireflow.controller;

import com.hireflow.entity.Application;
import com.hireflow.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(
            @PathVariable Long id) {

        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(
            @RequestBody Application application) {

        return ResponseEntity.ok(
                applicationService.createApplication(application)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(
            @PathVariable Long id,
            @RequestBody Application application) {

        return ResponseEntity.ok(
                applicationService.updateApplication(id, application)
        );
    }

    @PatchMapping("/{id}/stage")
    public ResponseEntity<Application> moveToStage(
            @PathVariable Long id,
            @RequestParam String stage) {

        return ResponseEntity.ok(
                applicationService.moveToStage(id, stage)
        );
    }

    @GetMapping("/funnel/{jobId}")
    public ResponseEntity<?> getPipelineFunnel(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                applicationService.getPipelineFunnel(jobId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id) {

        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}