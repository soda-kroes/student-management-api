package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.dto.request.StudentRequest;
import com.acledabank.student_management_api.dto.response.StudentResponse;
import com.acledabank.student_management_api.service.StudentService;
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
@RequestMapping("/api/v1/student")
public class StudentRestController {

    private final StudentService studentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        log.info("Create student request received: {}", JsonLogger.toJson(studentRequest));

        StudentResponse studentResponse = studentService.create(studentRequest);

        log.info("Student created successfully with ID: {}", studentResponse.getId());
        log.debug("Student details: {}", JsonLogger.toJson(studentResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Student created successfully.", studentResponse),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest studentRequest) {
        log.info("Update request received for student ID {}: {}", id, JsonLogger.toJson(studentRequest));

        StudentResponse studentResponse = studentService.update(id, studentRequest);

        log.info("Student updated successfully with ID: {}", id);
        log.debug("Updated student details: {}", JsonLogger.toJson(studentResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Student updated successfully.", studentResponse),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStudentById(@PathVariable(name = "id") Long id) {
        StudentResponse studentResponse = studentService.getById(id);

        log.info("Fetched student details for ID: {}", id);
        log.debug("Student details: {}", JsonLogger.toJson(studentResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Student fetched successfully.", studentResponse),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllStudents() {
        List<StudentResponse> studentResponseList = studentService.getAll();

        log.info("Fetched list of all students, total count: {}", studentResponseList.size());
        log.debug("Student list details: {}", JsonLogger.toJson(studentResponseList));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Students fetched successfully.", studentResponseList),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteStudentById(@PathVariable(name = "id") Long id) {
        studentService.delete(id);

        log.info("Deleted student with ID: {}", id);

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Student deleted successfully.", null),
                HttpStatus.OK
        );
    }
}
