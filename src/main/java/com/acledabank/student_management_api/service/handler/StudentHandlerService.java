package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.StudentRequest;
import com.acledabank.student_management_api.dto.response.*;
import com.acledabank.student_management_api.model.Course;
import com.acledabank.student_management_api.model.Department;
import com.acledabank.student_management_api.model.Enrollment;
import com.acledabank.student_management_api.model.Student;
import com.acledabank.student_management_api.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.acledabank.student_management_api.utils.DateTimeUtil.convertDateToString;

@Service
@Slf4j
public class StudentHandlerService {


    public Student convertStudentRequestToStudent(StudentRequest studentRequest, Student student, Department department, List<Course> courses, List<Enrollment> enrollments) {

        student.setStudentCode(generateUniqueStudentCode());
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setGender(studentRequest.getGender());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setAddress(studentRequest.getAddress());
        student.setStatus(Constant.ACTIVE);
        student.setDob(DateTimeUtil.convertStringToDate(studentRequest.getDob()));

        if (department != null) {
            student.setDepartment(department);
        }

        if (courses != null) {
            student.setCourses(courses);
        }
        if (enrollments != null) {
            student.setEnrollments(enrollments);
        }

        if (student.getId() == null) {
            student.setEnrollmentDate(LocalDateTime.now());
            student.setCreatedAt(LocalDateTime.now());
            student.setCreatedBy(Constant.SYSTEM);
        } else {
            student.setUpdatedAt(LocalDateTime.now());
            student.setUpdatedBy(Constant.SYSTEM);
        }

        return student;
    }


    public StudentResponse convertStudentToStudentResponse(Student student, List<FakeApiResponse> fakeApiResponse) {
        if (student == null) return null;

        DepartmentResponse departmentResponse = null;
        if (student.getDepartment() != null) {
            departmentResponse = DepartmentResponse.builder()
                    .id(student.getDepartment().getId())
                    .name(student.getDepartment().getName())
                    .build();
        }

        List<CourseResponse> courseResponses = null;
        if (student.getCourses() != null) {
            courseResponses = student.getCourses().stream()
                    .map(course -> CourseResponse.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .courseCode(course.getCourseCode())
                            .build())
                    .collect(Collectors.toList());
        }

        List<EnrollmentResponse> enrollmentResponses = null;
        if (student.getEnrollments() != null) {
            enrollmentResponses = student.getEnrollments().stream()
                    .map(enrollment -> EnrollmentResponse.builder()
                            .id(enrollment.getId())
                            .enrollmentDate(enrollment.getEnrollmentDate() != null ? enrollment.getEnrollmentDate().toString() : null)
                            .build())
                    .collect(Collectors.toList());
        }

        return StudentResponse.builder()
                .id(student.getId())
                .studentCode(student.getStudentCode())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .gender(student.getGender())
                .email(student.getEmail())
                .phone(student.getPhone())
                .dob(convertDateToString(student.getDob()))
                .address(student.getAddress())
                .enrollmentDate(student.getEnrollmentDate() != null ? student.getEnrollmentDate().toLocalDate() : null)
                .status(student.getStatus())
                .department(departmentResponse)
                .courses(courseResponses)
                .thirdPartyApiResponse(fakeApiResponse)
                .build();
    }


    private String generateUniqueStudentCode() {
        int randomNum = new Random().nextInt(9000) + 1000; // 4-digit number
        return "STU" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + randomNum;
    }


}
