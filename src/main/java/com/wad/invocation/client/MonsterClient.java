package com.wad.invocation.client;

import com.wad.invocation.dto.CreateMonsterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MonsterClient {

    private static final Logger log = LoggerFactory.getLogger(MonsterClient.class);
    private final RestTemplate restTemplate;

    @Value("${monster.service.url}")
    private String monsterServiceUrl;

    public MonsterClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createMonster(String token, CreateMonsterRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token.startsWith("Bearer ") ? token : "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateMonsterRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    monsterServiceUrl + "/api/monster",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // The MonsterResponse contains 'id'
                return (String) response.getBody().get("id");
            }
        } catch (Exception e) {
            log.error("Failed to create monster in MonsterService", e);
        }
        return null;
    }
}
