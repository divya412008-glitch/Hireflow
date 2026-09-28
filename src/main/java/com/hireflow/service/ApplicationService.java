package com.hireflow.service;

import com.hireflow.entity.Application;
import com.hireflow.entity.PipelineStage;
import com.hireflow.exception.PipelineTransitionException;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.FeedbackRepository;
import com.hireflow.repository.PipelineStageRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final PipelineStageRepository pipelineStageRepository;
    private final FeedbackRepository feedbackRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            PipelineStageRepository pipelineStageRepository,
            FeedbackRepository feedbackRepository) {

        this.applicationRepository = applicationRepository;
        this.pipelineStageRepository = pipelineStageRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public Application updateApplication(
            Long id,
            Application applicationDetails) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));

        application.setCandidate(applicationDetails.getCandidate());
        application.setJob(applicationDetails.getJob());
        application.setPipelineStage(applicationDetails.getPipelineStage());
        application.setAppliedAt(applicationDetails.getAppliedAt());

        return applicationRepository.save(application);
    }

    public Application moveToStage(Long id, String stageName) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));

        PipelineStage targetStage = pipelineStageRepository
                .findByName(stageName.toUpperCase())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pipeline stage not found"));

        if ("OFFER".equalsIgnoreCase(targetStage.getName())) {

            List<?> positiveFeedback =
                    feedbackRepository
                            .findByInterviewApplicationIdAndRecommendation(
                                    id,
                                    "POSITIVE"
                            );

            if (positiveFeedback.isEmpty()) {
                throw new PipelineTransitionException(
                        "Cannot move application to OFFER without positive interview feedback."
                );
            }
        }

        application.setPipelineStage(targetStage);

        return applicationRepository.save(application);
    }

    public Map<String, Long> getPipelineFunnel(Long jobId) {

        List<Object[]> results =
                applicationRepository.countApplicationsByStage(jobId);

        Map<String, Long> funnel = new LinkedHashMap<>();

        for (Object[] row : results) {
            String stageName = (String) row[0];
            Long count = ((Number) row[1]).longValue();

            funnel.put(stageName, count);
        }

        return funnel;
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}