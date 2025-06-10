package com.acledabank.student_management_api.utils;


import com.acledabank.student_management_api.dto.response.ApiResponseEntityDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponseUtil {
    public static ApiResponseEntityDto createApiResponseEntity(String errorCode, int statusCode, String message, String messageDescription, Object responseData) {
        return ApiResponseEntityDto.builder()
                .errorCode(errorCode)
                .statusCode(statusCode)
                .message(message)
                .messageDescription(messageDescription)
                .timeStamp(LocalDateTime.now())
                .responseData(responseData)
                .build();
    }

    public static ApiResponseEntityDto successResponse(String msgDescription, Object object) {
        return createApiResponseEntity(
                "200",
                200,
                "Request Successful",
                msgDescription,
                object
        );
    }

}