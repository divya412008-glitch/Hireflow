package com.hireflow.controller;

import com.hireflow.entity.InterviewPanel;
import com.hireflow.service.InterviewPanelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interview-panels")
public class InterviewPanelController {

    private final InterviewPanelService interviewPanelService;

    public InterviewPanelController(InterviewPanelService interviewPanelService) {
        this.interviewPanelService = interviewPanelService;
    }

    @GetMapping
    public List<InterviewPanel> getAllPanelAssignments() {
        return interviewPanelService.getAllPanelAssignments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewPanel> getPanelAssignmentById(
            @PathVariable Long id) {

        return interviewPanelService.getPanelAssignmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/interview/{interviewId}")
    public List<InterviewPanel> getPanelByInterviewId(
            @PathVariable Long interviewId) {

        return interviewPanelService.getPanelByInterviewId(interviewId);
    }

    @GetMapping("/interviewer/{interviewerId}")
    public List<InterviewPanel> getPanelByInterviewerId(
            @PathVariable Long interviewerId) {

        return interviewPanelService.getPanelByInterviewerId(interviewerId);
    }

    @PostMapping
    public ResponseEntity<InterviewPanel> createPanelAssignment(
            @RequestBody InterviewPanel panel) {

        return ResponseEntity.ok(
                interviewPanelService.createPanelAssignment(panel)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanelAssignment(
            @PathVariable Long id) {

        interviewPanelService.deletePanelAssignment(id);
        return ResponseEntity.noContent().build();
    }
}