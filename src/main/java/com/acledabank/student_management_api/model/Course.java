package com.acledabank.student_management_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tbl_course")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code",nullable = false)
    private String courseCode;

    @Column(name = "title",nullable = false)
    private String title;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
