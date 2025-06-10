package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.dto.request.DepartmentRequest;
import com.acledabank.student_management_api.dto.response.DepartmentResponse;
import com.acledabank.student_management_api.utils.ApiResponseUtil;
import com.acledabank.student_management_api.service.DepartmentService;
import com.acledabank.student_management_api.utils.JsonLogger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentRestController {

    private final DepartmentService departmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {
        log.info("Create department request received: {}", JsonLogger.toJson(departmentRequest));

        DepartmentResponse departmentResponse = departmentService.create(departmentRequest);

        log.info("Department created successfully with ID: {}", departmentResponse.getId());
        log.debug("Created department details: {}", JsonLogger.toJson(departmentResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Department created successfully.", departmentResponse),
                HttpStatus.CREATED
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDepartments() {
        log.info("Request received to fetch all departments");

        List<DepartmentResponse> departments = departmentService.getAll();

        log.info("Found {} departments", departments.size());
        log.debug("Departments list: {}", JsonLogger.toJson(departments));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Departments retrieved successfully.", departments),
                HttpStatus.OK
        );
    }
}

