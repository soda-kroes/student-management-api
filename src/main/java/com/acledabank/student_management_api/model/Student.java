package com.acledabank.student_management_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_student")
@Data
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String studentCode;

    private String firstName;
    private String lastName;
    private String gender;
    @Temporal(TemporalType.DATE)
    private Date dob = new Date();
    private String email;
    private String phone;
    private String address;
    private LocalDateTime enrollmentDate;
    private String status;

    // Many-to-One: Student belongs to one Department
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Many-to-Many: Student enrolls in many Courses
    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    // One-to-Many: Student has many Enrollments
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

}


