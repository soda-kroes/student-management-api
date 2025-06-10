package com.acledabank.student_management_api.dto.response;

import com.acledabank.student_management_api.dto.response.EnrollmentResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private Long id;
    private String studentCode;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String phone;
    private String address;
    private LocalDate enrollmentDate;
    private String status;
    private String grade;

    @JsonProperty("third_party_api_response")
    private List<FakeApiResponse> thirdPartyApiResponse;

    private DepartmentResponse department;

    private List<CourseResponse> courses;

}
