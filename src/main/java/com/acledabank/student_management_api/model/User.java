package com.acledabank.student_management_api.model;

import com.acledabank.student_management_api.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user") // Specify a custom table name here
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email",unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_ADMIN;

}