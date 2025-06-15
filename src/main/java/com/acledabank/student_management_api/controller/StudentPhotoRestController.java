package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.dto.request.StudentPhotoRequest;
import com.acledabank.student_management_api.dto.response.StudentPhotoResponse;
import com.acledabank.student_management_api.service.StudentPhotoService;
import com.acledabank.student_management_api.util.ApiResponseUtil;
import com.acledabank.student_management_api.util.JsonLogger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/student-photo")
public class StudentPhotoRestController {

    private final StudentPhotoService studentPhotoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStudentPhoto(@RequestParam("files") MultipartFile[] files, StudentPhotoRequest studentPhotoRequest) {
        log.info("========> Received request to upload student photos");
        log.info("Files count: {}", files.length);
        log.info("File names: {}", Arrays.stream(files).map(MultipartFile::getOriginalFilename).toList());
        log.info("Request payload: {}", JsonLogger.toJson(studentPhotoRequest));

        List<StudentPhotoResponse> studentPhotoResponses = studentPhotoService.upload(files, studentPhotoRequest);

        log.info("Student photo upload completed successfully");
        log.info("Response: {}", JsonLogger.toJson(studentPhotoResponses));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Student Photo Upload Success", studentPhotoResponses),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudentPhoto(@PathVariable(name = "id") Long id, @RequestBody StudentPhotoRequest studentPhotoRequest) {
        log.info("========> Received request to update student photo with ID: {}", id);
        log.info("Request payload: {}", JsonLogger.toJson(studentPhotoRequest));

        StudentPhotoResponse studentPhotoResponse = studentPhotoService.update(id, studentPhotoRequest);

        log.info("Student photo update completed successfully for ID: {}", id);
        log.info("Response: {}", JsonLogger.toJson(studentPhotoResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Student Photo Update Success", studentPhotoResponse),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStudentPhotoById(@PathVariable(name = "id") Long id) {
        log.info("========> Received request to get student photo by ID: {}", id);

        StudentPhotoResponse studentPhotoResponse = studentPhotoService.getById(id);

        log.info("Successfully retrieved student photo for ID: {}", id);
        log.info("Response: {}", JsonLogger.toJson(studentPhotoResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Get Student Photo Success", studentPhotoResponse),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteStudentPhoto(@PathVariable(name = "id") Long id) {
        log.info("========> Received request to delete student photo with ID: {}", id);

        studentPhotoService.delete(id);

        log.info("Successfully deleted student photo with ID: {}", id);

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Successfully deleted student photo.", null),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllStudentPhotos() {
        log.info("========> Received request to get all student photos");

        List<StudentPhotoResponse> allPhotos = studentPhotoService.getAll();

        log.info("Successfully retrieved all student photos. Count: {}", allPhotos.size());
        log.info("Response: {}", JsonLogger.toJson(allPhotos));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Get All Student Photos Success", allPhotos),
                HttpStatus.OK
        );
    }
}
