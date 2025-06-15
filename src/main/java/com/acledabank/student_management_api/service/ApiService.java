package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.dto.request.ApiObjectRequest;
import com.acledabank.student_management_api.dto.response.ApiObjectResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiService {
    // Create a new object
    Mono<ApiObjectResponse> createObject(ApiObjectRequest request);

    // Read all objects
    Flux<ApiObjectResponse> getAllObjects();

    // Read object by ID
    Mono<ApiObjectResponse> getObjectById(String id);

    // Update an existing object
    Mono<ApiObjectResponse> updateObject(String id, ApiObjectRequest request);

    // Delete an object
    Mono<Void> deleteObject(String id);
}
