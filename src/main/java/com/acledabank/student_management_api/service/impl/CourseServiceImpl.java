package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.dto.request.CourseRequest;
import com.acledabank.student_management_api.dto.response.CourseResponse;
import com.acledabank.student_management_api.exception.DuplicateResourceException;
import com.acledabank.student_management_api.exception.NotFoundErrorException;
import com.acledabank.student_management_api.model.Course;
import com.acledabank.student_management_api.reposity.CourseRepository;
import com.acledabank.student_management_api.service.CourseService;
import com.acledabank.student_management_api.service.handler.CourseHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseHandlerService courseHandlerService;

    @Override
    public CourseResponse create(CourseRequest courseRequest) {
        if (courseRepository.existsByCode(courseRequest.getCode())) {
            log.warn("Course with code '{}' already exists.", courseRequest.getCode());
            throw new DuplicateResourceException("Course with code '" + courseRequest.getCode() + "' already exists.");
        }

        Course course = courseHandlerService.convertCourseRequestToCourse(courseRequest);
        Course savedCourse = courseRepository.save(course);
        return courseHandlerService.convertCourseToCourseResponse(savedCourse);
    }

    @Override
    public List<CourseResponse> getAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseResponse> responseList = new ArrayList<>();

        for (Course course : courses) {
            responseList.add(courseHandlerService.convertCourseToCourseResponse(course));
        }

        return responseList;
    }

    @Override
    public CourseResponse update(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course with ID '{}' not found.", id);
                    return new NotFoundErrorException("Course with ID '" + id + "' not found.");
                });

        course.setCode(courseRequest.getCode());
        course.setTitle(courseRequest.getTitle());
        return courseHandlerService.convertCourseToCourseResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course with ID '{}' not found.", id);
                    return new NotFoundErrorException("Course with ID '" + id + "' not found.");
                });

        return courseHandlerService.convertCourseToCourseResponse(course);
    }

    @Override
    public void delete(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course with ID '{}' not found for deletion.", id);
                    return new NotFoundErrorException("Course with ID '" + id + "' not found.");
                });

        courseRepository.delete(course);
        log.info("Course with ID '{}' deleted successfully.", id);
    }
}
