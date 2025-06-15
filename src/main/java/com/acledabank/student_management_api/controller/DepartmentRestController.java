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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentRestController {

    private final DepartmentService departmentService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {
        log.info("Create department request received: {}", JsonLogger.toJson(departmentRequest));
        DepartmentResponse departmentResponse = departmentService.create(departmentRequest);
        log.info("Department created successfully with ID: {}", departmentResponse.getId());
        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Department created successfully.", departmentResponse),
                HttpStatus.OK
        );
    }

    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDepartments() {
        log.info("Request received to fetch all departments");
        List<DepartmentResponse> departments = departmentService.getAll();
        log.info("Found {} departments", departments.size());
        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Departments retrieved successfully.", departments),
                HttpStatus.OK
        );
    }

//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        log.info("Request received to fetch department with ID: {}", id);
        DepartmentResponse departmentResponse = departmentService.getById(id);
        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Department retrieved successfully.", departmentResponse),
                HttpStatus.OK
        );
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest departmentRequest) {
        log.info("Update request received for department ID: {}", id);
        DepartmentResponse updatedDepartment = departmentService.update(id, departmentRequest);
        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Department updated successfully.", updatedDepartment),
                HttpStatus.OK
        );
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        log.info("Delete request received for department ID: {}", id);
        departmentService.delete(id);
        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Department deleted successfully.", null),
                HttpStatus.OK
        );
    }
}
