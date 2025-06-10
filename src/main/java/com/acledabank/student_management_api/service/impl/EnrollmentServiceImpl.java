package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.dto.request.EnrollmentRequest;
import com.acledabank.student_management_api.dto.response.EnrollmentResponse;
import com.acledabank.student_management_api.exception.NotFoundErrorException;
import com.acledabank.student_management_api.model.Enrollment;
import com.acledabank.student_management_api.reposity.CourseRepository;
import com.acledabank.student_management_api.reposity.EnrollmentRepository;
import com.acledabank.student_management_api.reposity.StudentRepository;
import com.acledabank.student_management_api.service.EnrollmentService;
import com.acledabank.student_management_api.service.handler.EnrollmentHandlerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentHandlerService enrollmentHandlerService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public EnrollmentResponse create(EnrollmentRequest enrollmentRequest) {

        var studentOpt = studentRepository.findById(enrollmentRequest.getStudentId());
        if (studentOpt.isEmpty()) {
            throw new NotFoundErrorException("Student with Id " + enrollmentRequest.getStudentId() + " not found.");
        }
        var student = studentOpt.get();

        var courseOpt = courseRepository.findById(enrollmentRequest.getCourseId());
        if (courseOpt.isEmpty()) {
            throw new NotFoundErrorException("Course with Id " + enrollmentRequest.getCourseId() + " not found.");
        }
        var course = courseOpt.get();


        Enrollment enrollment = enrollmentHandlerService.convertEnrollmentRequestToEnrollment(enrollmentRequest, student, course);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return enrollmentHandlerService.convertEnrollmentToEnrollmentResponse(savedEnrollment);
    }
}
