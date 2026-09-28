package com.hireflow.controller;

import com.hireflow.entity.Interviewer;
import com.hireflow.service.InterviewerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviewers")
public class InterviewerController {

    private final InterviewerService interviewerService;

    public InterviewerController(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }

    @GetMapping
    public List<Interviewer> getAllInterviewers() {
        return interviewerService.getAllInterviewers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interviewer> getInterviewerById(
            @PathVariable Long id) {

        return interviewerService.getInterviewerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Interviewer> createInterviewer(
            @RequestBody Interviewer interviewer) {

        return ResponseEntity.ok(
                interviewerService.createInterviewer(interviewer)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interviewer> updateInterviewer(
            @PathVariable Long id,
            @RequestBody Interviewer interviewer) {

        return ResponseEntity.ok(
                interviewerService.updateInterviewer(id, interviewer)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewer(
            @PathVariable Long id) {

        interviewerService.deleteInterviewer(id);
        return ResponseEntity.noContent().build();
    }
}