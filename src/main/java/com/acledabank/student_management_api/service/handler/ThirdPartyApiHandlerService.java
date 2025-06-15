package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.dto.response.FakeApiResponse;
import com.acledabank.student_management_api.util.JsonLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThirdPartyApiHandlerService {

    private final WebClient webClient;

    @Value("${api.url}")
    private String apiUrl;

    public List<FakeApiResponse> makeRequestToFakeApi() {
        try {
            List<FakeApiResponse> response = webClient.get()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToFlux(FakeApiResponse.class)
                    .collectList()
                    .block();

            if (response != null && !response.isEmpty()) {
                log.info("Response from API: {}", JsonLogger.toJson(response));
                return response;
            } else {
                log.warn("Empty response from API");
                return Collections.emptyList();
            }

        } catch (WebClientResponseException e) {
            log.error("WebClientResponseException - Status: {}, Body: {}", e.getRawStatusCode(), e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception during API call", e);
            throw e;
        }
    }
}
