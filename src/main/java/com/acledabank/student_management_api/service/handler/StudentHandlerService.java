package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.StudentRequest;
import com.acledabank.student_management_api.dto.response.*;
import com.acledabank.student_management_api.enums.StudentStatus;
import com.acledabank.student_management_api.model.Department;
import com.acledabank.student_management_api.model.Enrollment;
import com.acledabank.student_management_api.model.Student;
import com.acledabank.student_management_api.model.StudentPhoto;
import com.acledabank.student_management_api.reposity.StudentPhotoRepository;
import com.acledabank.student_management_api.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.acledabank.student_management_api.util.DateTimeUtil.convertDateToString;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentHandlerService {
    private final StudentPhotoRepository studentPhotoRepository;

    public boolean verifyMenuItemPhoto(StudentRequest studentRequest) {
        // Verify menu item photo
        Set<Long> studentPhotoIds = new HashSet<>();
        for (Long photoId : studentRequest.getPhotoIds()) {
            studentPhotoIds.add(photoId); // Extract IDs
        }
        List<StudentPhoto> studentPhotos = studentPhotoRepository.findAllByIdIn(studentPhotoIds);
        if (studentPhotos.isEmpty()) {
            log.warn("Student photo(s) not found for IDs: {}", studentPhotoIds);
            return false;
        }

        return true;
    }


    public Student convertStudentRequestToStudent(StudentRequest studentRequest, Student student, Department department, List<Enrollment> enrollments) {

        student.setStudentCode(generateUniqueStudentCode());
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setGender(studentRequest.getGender());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setAddress(studentRequest.getAddress());
        student.setStatus(StudentStatus.valueOf(Constant.ACTIVE));
        student.setDob(DateTimeUtil.convertStringToDate(studentRequest.getDob()));

        if (department != null) {
            student.setDepartment(department);
        }

        if (enrollments != null) {
            student.setEnrollments(enrollments);
        }

        if (student.getId() == null) {
            student.setCreatedAt(LocalDateTime.now());
            student.setCreatedBy(Constant.SYSTEM);
        } else {
            student.setUpdatedAt(LocalDateTime.now());
            student.setUpdatedBy(Constant.SYSTEM);
        }

        if (student.getId() == null) {
            student.setCreatedAt(LocalDateTime.now());
            student.setCreatedBy(Constant.SYSTEM);
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
                            .courseCode(course.getCode())
                            .build())
                    .collect(Collectors.toList());
        }

        List<EnrollmentResponse> enrollmentResponses = null;
        if (student.getEnrollments() != null) {
            enrollmentResponses = student.getEnrollments().stream()
                    .map(enrollment -> EnrollmentResponse.builder()
                            .id(enrollment.getId())
                            .course(enrollment.getCourse())
                            .enrollmentDate(enrollment.getEnrollmentDate() != null ? enrollment.getEnrollmentDate().toString() : null)
                            .grade(enrollment.getGrade())
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
                .status(String.valueOf(student.getStatus()))
                .department(departmentResponse)
                .enrollments(enrollmentResponses)
                .thirdPartyApiResponse(fakeApiResponse)
                .build();
    }


    private String generateUniqueStudentCode() {
        int randomNum = new Random().nextInt(9000) + 1000; // 4-digit number
        return "STU" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + randomNum;
    }


}
