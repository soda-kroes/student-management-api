package com.acledabank.student_management_api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class ApiObjectResponse {
    private String id;
    private String name;

    // You can use a Map for flexible key-value pairs in `data`
    private Map<String, Object> data;

    @JsonProperty("createdAt")
    private ZonedDateTime createdAt;
}
