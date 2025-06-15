package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.dto.request.EnrollmentRequest;
import com.acledabank.student_management_api.dto.response.EnrollmentResponse;
import com.acledabank.student_management_api.service.EnrollmentService;
import com.acledabank.student_management_api.util.ApiResponseUtil;
import com.acledabank.student_management_api.util.JsonLogger;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/enrollment")
public class EnrollmentRestController {

    private final EnrollmentService enrollmentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createEnrollment(@Valid @RequestBody EnrollmentRequest enrollmentRequest) {
        log.info("Received create enrollment request: {}", JsonLogger.toJson(enrollmentRequest));

        EnrollmentResponse enrollmentResponse = enrollmentService.create(enrollmentRequest);
        log.info("Enrollment created successfully: {}", JsonLogger.toJson(enrollmentResponse));

        Object response = ApiResponseUtil.successResponse("Create Enrollment Success.", enrollmentResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
