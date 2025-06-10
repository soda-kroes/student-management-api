package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.dto.response.FakeApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
                    .bodyToFlux(FakeApiResponse.class)  // Deserialize JSON array into Flux<FakeApiResponse>
                    .collectList()                     // Collect into List<FakeApiResponse>
                    .block();                         // Blocking call for simplicity

            log.info("Response from API: {}", response);

            return response;

        } catch (WebClientResponseException e) {
            log.error("Error response from API: status {}, body {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while calling API", e);
            throw e;
        }
    }
}


