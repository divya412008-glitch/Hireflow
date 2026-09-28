package com.hireflow.service;

import com.hireflow.entity.Interviewer;
import com.hireflow.repository.InterviewerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewerService {

    private final InterviewerRepository interviewerRepository;

    public InterviewerService(InterviewerRepository interviewerRepository) {
        this.interviewerRepository = interviewerRepository;
    }

    public List<Interviewer> getAllInterviewers() {
        return interviewerRepository.findAll();
    }

    public Optional<Interviewer> getInterviewerById(Long id) {
        return interviewerRepository.findById(id);
    }

    public Interviewer createInterviewer(Interviewer interviewer) {
        return interviewerRepository.save(interviewer);
    }

    public Interviewer updateInterviewer(
            Long id,
            Interviewer interviewerDetails) {

        Interviewer interviewer = interviewerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interviewer not found"));

        interviewer.setName(interviewerDetails.getName());
        interviewer.setEmail(interviewerDetails.getEmail());
        interviewer.setRole(interviewerDetails.getRole());
        interviewer.setSpecialization(interviewerDetails.getSpecialization());

        return interviewerRepository.save(interviewer);
    }

    public void deleteInterviewer(Long id) {
        interviewerRepository.deleteById(id);
    }
}