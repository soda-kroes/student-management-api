package com.acledabank.student_management_api.util;

import com.acledabank.student_management_api.dto.response.ApiResponseEntityDto;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class ApiResponseUtil {

    public ApiResponseEntityDto createApiResponseEntity(String errorCode, int statusCode, String message, String messageDescription, Object responseData) {
        return ApiResponseEntityDto.builder()
                .errorCode(errorCode)
                .statusCode(statusCode)
                .message(message)
                .messageDescription(messageDescription)
                .timeStamp(LocalDateTime.now()) // consider ZonedDateTime for timezone consistency
                .responseData(responseData)
                .build();
    }

    public ApiResponseEntityDto successResponse(String msgDescription, Object object) {
        return createApiResponseEntity(
                "200",
                200,
                "Request Successful",
                msgDescription,
                object
        );
    }

    public ApiResponseEntityDto errorResponse(int statusCode, String errorCode, String message, String messageDescription, Object responseData) {
        return createApiResponseEntity(errorCode, statusCode, message, messageDescription, responseData);
    }
}
