package com.acledabank.student_management_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_enrollment")
@Data
public class Enrollment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDateTime enrollmentDate = LocalDateTime.now();
    private String grade;
}
