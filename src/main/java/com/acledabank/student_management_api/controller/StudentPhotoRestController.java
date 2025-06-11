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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStudentPhoto(@RequestParam("files") MultipartFile[] files, StudentPhotoRequest studentPhotoRequest) {
        // Log incoming request
        log.info("========> Received request to upload student photos");
        log.info("Files count: {}", files.length);
        log.info("File names: {}", Arrays.stream(files).map(MultipartFile::getOriginalFilename).toList());
        log.info("Request payload: {}", JsonLogger.toJson(studentPhotoRequest));

        List<StudentPhotoResponse> studentPhotoResponses = studentPhotoService.upload(files, studentPhotoRequest);

        // Log successful response
        log.info("Student photo upload completed successfully");
        log.info("Response: {}", JsonLogger.toJson(studentPhotoResponses));

        Object response = ApiResponseUtil.successResponse("Student Photo Upload Success", studentPhotoResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudentPhoto(@PathVariable(name = "id") Long id, @RequestBody StudentPhotoRequest studentPhotoRequest) {
        // Log incoming request
        log.info("========> Received request to update student photo with ID: {}", id);
        log.info("Request payload: {}", JsonLogger.toJson(studentPhotoRequest));

        StudentPhotoResponse studentPhotoResponse = studentPhotoService.update(id, studentPhotoRequest);

        // Log successful response
        log.info("Student photo update completed successfully for ID: {}", id);
        log.info("Response: {}", JsonLogger.toJson(studentPhotoResponse));

        Object response = ApiResponseUtil.successResponse("Student Photo Update Success", studentPhotoResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStudentPhotoById(@PathVariable(name = "id") Long id) {
        // Log incoming request
        log.info("========> Received request to get student photo by ID: {}", id);

        StudentPhotoResponse studentPhotoResponse = studentPhotoService.getById(id);

        // Log successful response
        log.info("Successfully retrieved student photo for ID: {}", id);
        log.info("Response: {}", JsonLogger.toJson(studentPhotoResponse));

        Object response = ApiResponseUtil.successResponse("Get Student Photo Success", studentPhotoResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteStudentPhoto(@PathVariable(name = "id") Long id) {
        // Log incoming request
        log.info("========> Received request to delete student photo with ID: {}", id);

        studentPhotoService.delete(id);

        // Log successful response
        log.info("Successfully deleted student photo with ID: {}", id);

        Object response = ApiResponseUtil.successResponse("Successfully deleted student photo.", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllStudentPhotos() {
        // Log incoming request
        log.info("========> Received request to get all student photos");

        List<StudentPhotoResponse> allPhotos = studentPhotoService.getAll();

        // Log successful response
        log.info("Successfully retrieved all student photos. Count: {}", allPhotos.size());
        log.info("Response: {}", JsonLogger.toJson(allPhotos));

        Object response = ApiResponseUtil.successResponse("Get All Student Photos Success", allPhotos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}