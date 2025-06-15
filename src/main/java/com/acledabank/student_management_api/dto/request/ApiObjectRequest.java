package com.acledabank.student_management_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class ApiObjectRequest {
    private String id;
    private String name;

    // You can use a Map for flexible key-value pairs in `data`
    private Map<String, Object> data;
}
