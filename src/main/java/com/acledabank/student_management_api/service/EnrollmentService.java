package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.dto.request.EnrollmentRequest;
import com.acledabank.student_management_api.dto.response.EnrollmentResponse;

public interface EnrollmentService {
    EnrollmentResponse create (EnrollmentRequest enrollmentRequest);
}
