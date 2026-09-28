package com.hireflow.service;

import com.hireflow.entity.Interview;
import com.hireflow.repository.InterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;

    public InterviewService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    public Optional<Interview> getInterviewById(Long id) {
        return interviewRepository.findById(id);
    }

    public Interview createInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    public Interview updateInterview(Long id, Interview interviewDetails) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setApplication(interviewDetails.getApplication());
        interview.setStartTime(interviewDetails.getStartTime());
        interview.setEndTime(interviewDetails.getEndTime());
        interview.setInterviewType(interviewDetails.getInterviewType());
        interview.setMeetingLink(interviewDetails.getMeetingLink());
        interview.setStatus(interviewDetails.getStatus());

        return interviewRepository.save(interview);
    }

    public void deleteInterview(Long id) {
        interviewRepository.deleteById(id);
    }
}