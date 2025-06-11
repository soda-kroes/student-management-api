package com.acledabank.student_management_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class StudentRequest {

    @JsonProperty("first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotBlank(message = "Date of birth is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of birth must be in the format YYYY-MM-DD")
    private String dob;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "Phone number must be valid and contain only digits (optionally starting with +)")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;


    @JsonProperty("department_id")
    @NotNull(message = "Department ID is required")
    private Long departmentId;

    private List<@NotNull EnrollmentRequest> enrollments;

    @JsonProperty("photo_ids")
    private List<@NotNull(message = "Student photo ID cannot be null") Long> photoIds;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", updatable = false)
    private String createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at", insertable = false)
    private String updatedAt;

}
