package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.dto.request.CourseRequest;
import com.acledabank.student_management_api.dto.response.CourseResponse;
import com.acledabank.student_management_api.utils.ApiResponseUtil;
import com.acledabank.student_management_api.service.CourseService;
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
@RequestMapping("/api/v1/course")
public class CourseRestController {

    private final CourseService courseService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewCourse(@Valid @RequestBody CourseRequest courseRequest) {
        log.info("Create course request received: {}", JsonLogger.toJson(courseRequest));

        CourseResponse courseResponse = courseService.create(courseRequest);

        log.info("Course created successfully with ID: {}", courseResponse.getId());
        log.debug("Course creation response: {}", JsonLogger.toJson(courseResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Course has been successfully created.", courseResponse),
                HttpStatus.CREATED
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCourses() {
        log.info("Request received to fetch all courses");

        List<CourseResponse> courseResponseList = courseService.getAll();

        log.info("Fetched {} courses.", courseResponseList.size());
        log.debug("Courses response: {}", JsonLogger.toJson(courseResponseList));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("Courses retrieved successfully.", courseResponseList),
                HttpStatus.OK
        );
    }
}

