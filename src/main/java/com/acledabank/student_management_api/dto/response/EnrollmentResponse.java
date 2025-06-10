package com.acledabank.student_management_api.dto.response;

import com.acledabank.student_management_api.model.Course;
import com.acledabank.student_management_api.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    private Long id;
    private Student student;
    private Course course;
    private String enrollmentDate;
    private String grade;
}
