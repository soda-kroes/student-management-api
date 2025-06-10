package com.acledabank.student_management_api.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseEntityDto {

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("message_description")
    private String messageDescription;

    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;

    @JsonProperty("response_data")
    private Object responseData;
}
