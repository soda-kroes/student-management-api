package com.acledabank.student_management_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tbl_course")
@Data
public class Course extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code",nullable = false,unique = true)
    private String code;

    @Column(name = "title",nullable = false)
    private String title;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students;
}
