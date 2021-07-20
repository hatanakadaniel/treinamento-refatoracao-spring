package com.hatanaka.book.api;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TestWebClient {

    private final TestRestTemplate restTemplate;

    public ResponseEntity<?> doGet(final Map<String, String> headers, final String url) {
        return doRequest(headers, url, HttpMethod.GET, "");
    }

    public ResponseEntity<?> doPost(final Map<String, String> headers, final String url, final String payload) {
        return doRequest(headers, url, HttpMethod.POST, payload);
    }

    private ResponseEntity<?> doRequest(final Map<String, String> headers, final String url, HttpMethod httpMethod, final String payload) {
        try {
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            headers.forEach(httpHeaders::addIfAbsent);
            final HttpEntity<String> httpEntity = new HttpEntity<>(payload, httpHeaders);
            return restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build();
        }
    }
}
