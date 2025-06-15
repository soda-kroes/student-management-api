package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.dto.request.ApiObjectRequest;
import com.acledabank.student_management_api.dto.response.ApiObjectResponse;
import com.acledabank.student_management_api.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiServiceImpl implements ApiService {

    private final WebClient webClient;

    @Override
    public Mono<ApiObjectResponse> createObject(ApiObjectRequest request) {
        return null;
    }

    @Override
    public Flux<ApiObjectResponse> getAllObjects() {
        log.info("Fetching all objects");

        return webClient
                .get()
                .uri("https://api.restful-api.dev/objects")
                .retrieve()
                .bodyToFlux(ApiObjectResponse.class)
                .doOnComplete(() -> log.info("Successfully fetched all objects"))
                .doOnError(error -> log.error("Error fetching all objects: {}", error.getMessage()))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("HTTP error fetching objects. Status: {}, Body: {}",
                            ex.getStatusCode(), ex.getResponseBodyAsString());
                    return Flux.error(new RuntimeException("Failed to fetch objects: " + ex.getMessage()));
                });
    }

    @Override
    public Mono<ApiObjectResponse> getObjectById(String id) {
        return null;
    }

    @Override
    public Mono<ApiObjectResponse> updateObject(String id, ApiObjectRequest request) {
        return null;
    }

    @Override
    public Mono<Void> deleteObject(String id) {
        return null;
    }
}
