package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.dto.request.CourseRequest;
import com.acledabank.student_management_api.dto.response.CourseResponse;
import com.acledabank.student_management_api.model.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseHandlerService {

    public Course convertCourseRequestToCourse(CourseRequest courseRequest){
        Course course = new Course();
        course.setCourseCode(courseRequest.getCourseCode());
        course.setTitle(courseRequest.getTitle());
        return course;
    }

    public CourseResponse convertCourseToCourseResponse(Course course){
        return CourseResponse.builder()
                .id(course.getId())
                .courseCode(course.getCourseCode())
                .title(course.getTitle())
                .build();

    }
}
