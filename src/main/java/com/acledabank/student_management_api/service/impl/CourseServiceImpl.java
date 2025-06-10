package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.dto.request.CourseRequest;
import com.acledabank.student_management_api.dto.response.CourseResponse;
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
        Course course = courseHandlerService.convertCourseRequestToCourse(courseRequest);
        Course courseEntity = courseRepository.save(course);
        return courseHandlerService.convertCourseToCourseResponse(courseEntity);
    }

    @Override
    public List<CourseResponse> getAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseResponse> courseResponseList = new ArrayList<>();
        for (Course course : courses) {
            CourseResponse courseResponse = courseHandlerService.convertCourseToCourseResponse(course);
            courseResponseList.add(courseResponse);
        }
        return courseResponseList;
    }
}
