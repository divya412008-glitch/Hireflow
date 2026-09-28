package com.hireflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    @NotNull(message = "Application is required")
    private Application application;

    @Column(nullable = false)
    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @Column(nullable = false)
    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @Column(nullable = false)
    @NotBlank(message = "Interview type is required")
    private String interviewType;

    private String meetingLink;

    @Column(nullable = false)
    @NotBlank(message = "Interview status is required")
    private String status;
}