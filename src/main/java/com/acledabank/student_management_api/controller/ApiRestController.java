package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.dto.response.ApiObjectResponse;
import com.acledabank.student_management_api.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/third-party-service")
@Slf4j
@RequiredArgsConstructor
public class ApiRestController {
    private final ApiService apiService;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllTest() {
        return "HELLO";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ApiObjectResponse> getAllObjects() {
        return apiService.getAllObjects();
    }
}
