package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.EnrollmentRequest;
import com.acledabank.student_management_api.dto.request.StudentRequest;
import com.acledabank.student_management_api.dto.response.FakeApiResponse;
import com.acledabank.student_management_api.dto.response.StudentResponse;
import com.acledabank.student_management_api.exception.DuplicateResourceException;
import com.acledabank.student_management_api.exception.NotFoundErrorException;
import com.acledabank.student_management_api.model.Course;
import com.acledabank.student_management_api.model.Enrollment;
import com.acledabank.student_management_api.model.Student;
import com.acledabank.student_management_api.reposity.CourseRepository;
import com.acledabank.student_management_api.reposity.DepartmentRepository;
import com.acledabank.student_management_api.reposity.StudentRepository;
import com.acledabank.student_management_api.service.StudentService;
import com.acledabank.student_management_api.service.handler.StudentHandlerService;
import com.acledabank.student_management_api.service.handler.ThirdPartyApiHandlerService;
import com.acledabank.student_management_api.utils.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentHandlerService studentHandlerService;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final ThirdPartyApiHandlerService thirdPartyApiHandlerService;


    @Override
    public StudentResponse create(StudentRequest studentRequest) {
        Student student = new Student();

        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentRequest.getEmail() + " already exists.");
        }

        var department = departmentRepository.findById(studentRequest.getDepartmentId())
                .orElseThrow(() -> new NotFoundErrorException("Department with ID " + studentRequest.getDepartmentId() + " not found."));

        List<Course> courses = new ArrayList<>();
        if (studentRequest.getCourseIds() != null && !studentRequest.getCourseIds().isEmpty()) {
            courses = courseRepository.findAllById(studentRequest.getCourseIds());
        }

        List<Enrollment> enrollments = new ArrayList<>();
        if (studentRequest.getEnrollments() != null && !studentRequest.getEnrollments().isEmpty()) {
            for (EnrollmentRequest enrollmentRequest : studentRequest.getEnrollments()) {
                Course course = courseRepository.findById(enrollmentRequest.getCourseId())
                        .orElseThrow(() -> new NotFoundErrorException("Course not found: " + enrollmentRequest.getCourseId()));
                Enrollment enrollment = new Enrollment();
                enrollment.setCourse(course);
                enrollment.setGrade(enrollmentRequest.getGrade());
                enrollment.setStudent(student);
                enrollments.add(enrollment);
            }
        }

        student = studentHandlerService.convertStudentRequestToStudent(
                studentRequest, student, department, courses, enrollments
        );

        Student savedStudent = studentRepository.save(student);

        //call to fake api use web client
        List<FakeApiResponse> fakeApiResponses = thirdPartyApiHandlerService.makeRequestToFakeApi();

        return studentHandlerService.convertStudentToStudentResponse(savedStudent, fakeApiResponses);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundErrorException("Student with Id = " + id + " not found."));

        var department = departmentRepository.findById(studentRequest.getDepartmentId())
                .orElseThrow(() -> new NotFoundErrorException("Department with ID " + studentRequest.getDepartmentId() + " not found."));

        List<Course> courses = new ArrayList<>();
        if (studentRequest.getCourseIds() != null && !studentRequest.getCourseIds().isEmpty()) {
            courses = courseRepository.findAllById(studentRequest.getCourseIds());
        }

        List<Enrollment> newEnrollments = new ArrayList<>();
        if (studentRequest.getEnrollments() != null && !studentRequest.getEnrollments().isEmpty()) {
            for (EnrollmentRequest enrollmentRequest : studentRequest.getEnrollments()) {
                Course course = courseRepository.findById(enrollmentRequest.getCourseId())
                        .orElseThrow(() -> new NotFoundErrorException("Course not found: " + enrollmentRequest.getCourseId()));
                Enrollment enrollment = new Enrollment();
                enrollment.setCourse(course);
                enrollment.setGrade(enrollmentRequest.getGrade());
                enrollment.setStudent(student);
                newEnrollments.add(enrollment);
            }
        }

        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setGender(studentRequest.getGender());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setAddress(studentRequest.getAddress());
        student.setStatus(Constant.ACTIVE);
        student.setDob(DateTimeUtil.convertStringToDate(studentRequest.getDob()));
        student.setDepartment(department);
        student.setCourses(courses);

        //Fix orphan removal issue
        if (student.getEnrollments() == null) {
            student.setEnrollments(new ArrayList<>());
        } else {
            student.getEnrollments().clear();
        }
        student.getEnrollments().addAll(newEnrollments);

        Student updatedStudent = studentRepository.save(student);

        return studentHandlerService.convertStudentToStudentResponse(updatedStudent, null);
    }

    @Override
    public StudentResponse getById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundErrorException("Student with Id = " + id + " not found."));


        return studentHandlerService.convertStudentToStudentResponse(student, null);
    }

    @Override
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundErrorException("Student with Id = " + id + " not found."));

        // Initialize lazy collections here:
        student.getEnrollments().size();
        student.getCourses().size();

        studentRepository.deleteById(id);

    }


    @Override
    public List<StudentResponse> getAll() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for (Student student : students) {
            StudentResponse studentResponse = studentHandlerService.convertStudentToStudentResponse(student, null);
            studentResponseList.add(studentResponse);
        }
        return studentResponseList;
    }
}
