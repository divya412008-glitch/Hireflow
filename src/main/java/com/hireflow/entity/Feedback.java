package com.hireflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private Interviewer interviewer;

    @Column(nullable = false)
    private Integer rating;

    @Column(length = 2000)
    private String comments;

    @Column(nullable = false)
    private String recommendation;
}