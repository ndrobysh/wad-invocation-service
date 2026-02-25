package com.wad.invocation.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthClient {

    private static final Logger log = LoggerFactory.getLogger(AuthClient.class);
    private final RestTemplate restTemplate;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    public AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Remove "Bearer " prefix if it exists to send pure token to auth validation
        String pureToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // The auth service expects a JSON with "token"
        Map<String, String> requestMap = Map.of("token", pureToken);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestMap, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    authServiceUrl + "/api/auth/validate",
                    request,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Boolean isValid = (Boolean) response.getBody().get("valid");
                if (isValid != null && isValid) {
                    return (String) response.getBody().get("username");
                }
            }
        } catch (Exception e) {
            log.error("Token validation failed", e);
        }
        return null;
    }
}
