package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.dto.request.DepartmentRequest;
import com.acledabank.student_management_api.dto.response.DepartmentResponse;
import com.acledabank.student_management_api.service.DepartmentService;
import com.acledabank.student_management_api.util.ApiResponseUtil;
import com.acledabank.student_management_api.util.JsonLogger;
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        log.info("Request received to fetch department with ID: {}", id);

        DepartmentResponse departmentResponse = departmentService.getById(id);

        log.info("Department found with ID: {}", departmentResponse.getId());
        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Department retrieved successfully.", departmentResponse),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest departmentRequest) {
        log.info("Update request received for department ID: {}", id);
        log.debug("Update request body: {}", JsonLogger.toJson(departmentRequest));

        DepartmentResponse updatedDepartment = departmentService.update(id, departmentRequest);

        log.info("Department with ID {} updated successfully", id);
        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Department updated successfully.", updatedDepartment),
                HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        log.info("Delete request received for department ID: {}", id);

        departmentService.delete(id);

        log.info("Department with ID {} deleted successfully", id);
        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Department deleted successfully.", null),
                HttpStatus.OK
        );
    }
}
