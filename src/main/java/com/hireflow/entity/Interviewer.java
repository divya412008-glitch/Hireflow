package com.hireflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interviewers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String role;

    private String specialization;
}