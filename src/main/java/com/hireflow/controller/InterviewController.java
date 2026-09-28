package com.hireflow.controller;

import com.hireflow.entity.Interview;
import com.hireflow.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping
    public List<Interview> getAllInterviews() {
        return interviewService.getAllInterviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterviewById(
            @PathVariable Long id) {

        return interviewService.getInterviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Interview> createInterview(
            @Valid @RequestBody Interview interview) {

        return ResponseEntity.ok(
                interviewService.createInterview(interview)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interview> updateInterview(
            @PathVariable Long id,
            @Valid @RequestBody Interview interview) {

        return ResponseEntity.ok(
                interviewService.updateInterview(id, interview)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(
            @PathVariable Long id) {

        interviewService.deleteInterview(id);
        return ResponseEntity.noContent().build();
    }
}