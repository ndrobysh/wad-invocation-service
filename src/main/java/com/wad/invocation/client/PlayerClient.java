package com.wad.invocation.client;

import com.wad.invocation.dto.AddMonsterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlayerClient {

    private static final Logger log = LoggerFactory.getLogger(PlayerClient.class);
    private final RestTemplate restTemplate;

    @Value("${player.service.url}")
    private String playerServiceUrl;

    public PlayerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean addMonsterToPlayer(String token, String monsterId) {
        HttpHeaders headers = new HttpHeaders();
        // Forward token since PlayerService uses authService.validateToken
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        AddMonsterRequest request = new AddMonsterRequest(monsterId);
        HttpEntity<AddMonsterRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    playerServiceUrl + "/api/players/monsters",
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("Failed to add monster to Player in PlayerService", e);
            return false;
        }
    }
}
