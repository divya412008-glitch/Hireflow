package com.hireflow.service;

import com.hireflow.entity.Interview;
import com.hireflow.entity.InterviewPanel;
import com.hireflow.exception.InterviewConflictException;
import com.hireflow.repository.InterviewPanelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewPanelService {

    private final InterviewPanelRepository interviewPanelRepository;

    public InterviewPanelService(
            InterviewPanelRepository interviewPanelRepository) {
        this.interviewPanelRepository = interviewPanelRepository;
    }

    public List<InterviewPanel> getAllPanelAssignments() {
        return interviewPanelRepository.findAll();
    }

    public Optional<InterviewPanel> getPanelAssignmentById(Long id) {
        return interviewPanelRepository.findById(id);
    }

    public List<InterviewPanel> getPanelByInterviewId(Long interviewId) {
        return interviewPanelRepository.findByInterviewId(interviewId);
    }

    public List<InterviewPanel> getPanelByInterviewerId(Long interviewerId) {
        return interviewPanelRepository.findByInterviewerId(interviewerId);
    }

    public InterviewPanel createPanelAssignment(InterviewPanel panel) {

        Interview interview = panel.getInterview();

        List<InterviewPanel> conflicts =
                interviewPanelRepository.findConflictingAssignments(
                        panel.getInterviewer().getId(),
                        interview.getStartTime(),
                        interview.getEndTime()
                );

        if (!conflicts.isEmpty()) {
            throw new InterviewConflictException(
                    "Interviewer is unavailable during the selected time."
            );
        }

        return interviewPanelRepository.save(panel);
    }

    public void deletePanelAssignment(Long id) {
        interviewPanelRepository.deleteById(id);
    }
}