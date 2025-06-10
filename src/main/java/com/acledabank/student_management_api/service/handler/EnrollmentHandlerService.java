package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.dto.request.EnrollmentRequest;
import com.acledabank.student_management_api.dto.response.EnrollmentResponse;
import com.acledabank.student_management_api.model.Course;
import com.acledabank.student_management_api.model.Enrollment;
import com.acledabank.student_management_api.model.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EnrollmentHandlerService {
    public Enrollment convertEnrollmentRequestToEnrollment(EnrollmentRequest enrollmentRequest, Student student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setGrade(enrollmentRequest.getGrade());
        if (student != null) {
            enrollment.setStudent(student);
        }

        if (course != null) {
            enrollment.setCourse(course);
        }

        return enrollment;

    }

    public EnrollmentResponse convertEnrollmentToEnrollmentResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .student(enrollment.getStudent())
                .grade(enrollment.getGrade())
                .course(enrollment.getCourse())
                .build();
    }
}
