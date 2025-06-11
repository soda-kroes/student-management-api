package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.CourseRequest;
import com.acledabank.student_management_api.dto.response.CourseResponse;
import com.acledabank.student_management_api.model.Course;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CourseHandlerService {

    public Course convertCourseRequestToCourse(CourseRequest courseRequest,Course course){
        course.setCode(courseRequest.getCode());
        course.setTitle(courseRequest.getTitle());
        if (course.getId()==null){
            course.setCreatedAt(LocalDateTime.now());
            course.setCreatedBy(Constant.SYSTEM);
        }
        return course;
    }

    public CourseResponse convertCourseToCourseResponse(Course course){
        return CourseResponse.builder()
                .id(course.getId())
                .courseCode(course.getCode())
                .title(course.getTitle())
                .build();

    }
}
