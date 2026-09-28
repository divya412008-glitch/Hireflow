package com.hireflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "interview_panel",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"interview_id", "interviewer_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewPanel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private Interviewer interviewer;

    private String panelRole;
}