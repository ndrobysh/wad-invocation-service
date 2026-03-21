package com.wad.invocation.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wad.invocation.dto.AddMonsterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PlayerClient {

    private static final Logger log = LoggerFactory.getLogger(PlayerClient.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${player.service.url}")
    private String playerServiceUrl;

    public PlayerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @return null si succes, message d'erreur sinon
     */
    public String addMonsterToPlayer(String token, String monsterId) {
        HttpHeaders headers = new HttpHeaders();
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
            return response.getStatusCode().is2xxSuccessful() ? null : "Erreur inconnue du service joueur";
        } catch (HttpClientErrorException e) {
            String msg = extractMessage(e.getResponseBodyAsString());
            log.warn("Player service a rejete la requete ({}): {}", e.getStatusCode(), msg);
            return msg;
        } catch (Exception e) {
            log.error("Impossible de contacter le service joueur", e);
            return "Service joueur indisponible";
        }
    }

    private String extractMessage(String body) {
        try {
            JsonNode node = objectMapper.readTree(body);
            if (node.has("message")) return node.get("message").asText();
            if (node.has("error")) return node.get("error").asText();
        } catch (Exception ignored) {}
        return body;
    }
}
