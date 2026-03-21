package com.wad.invocation.controller;

import com.wad.invocation.dto.InvocationResponse;
import com.wad.invocation.model.BaseMonster;
import com.wad.invocation.model.InvocationLog;
import com.wad.invocation.repository.BaseMonsterRepository;
import com.wad.invocation.repository.InvocationLogRepository;
import com.wad.invocation.service.InvocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invocation")
@Tag(name = "Invocation", description = "API pour l'invocation de monstres Gacha")
public class InvocationController {

    private final InvocationService invocationService;
    private final BaseMonsterRepository baseMonsterRepository;
    private final InvocationLogRepository invocationLogRepository;

    public InvocationController(InvocationService invocationService, BaseMonsterRepository baseMonsterRepository, InvocationLogRepository invocationLogRepository) {
        this.invocationService = invocationService;
        this.baseMonsterRepository = baseMonsterRepository;
        this.invocationLogRepository = invocationLogRepository;
    }

    @Operation(summary = "Invoquer un monstre", description = "Génère un monstre aléatoirement et l'assigne au joueur (nécessite un token valide)")
    @PostMapping("/invoke")
    public ResponseEntity<InvocationResponse> invokeMonster(@RequestHeader("Authorization") String token) {
        InvocationResponse response = invocationService.invokeMonster(token);

        if (response == null || response.getStatus() == null || response.getStatus().equals("UNAUTHORIZED")) {
            return ResponseEntity.status(401).body(response);
        }

        if (response.getStatus().equals("FAILED")) {
            int status = isUserError(response.getMessage()) ? 400 : 500;
            return ResponseEntity.status(status).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Recréer une invocation échouée", description = "Tente de finaliser une invocation bloquée dans la base tampon")
    @PostMapping("/recreate/{logId}")
    public ResponseEntity<InvocationResponse> recreateInvocation(@PathVariable String logId, @RequestHeader("Authorization") String token) {
        InvocationResponse response = invocationService.recreateFailedInvocation(logId, token);

        if (response.getStatus() != null && response.getStatus().equals("UNAUTHORIZED")) {
            return ResponseEntity.status(401).body(response);
        }

        if (response.getStatus() != null && response.getStatus().equals("FAILED")) {
            int status = isUserError(response.getMessage()) ? 400 : 500;
            return ResponseEntity.status(status).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lister les monstres invocables", description = "Affiche la liste de tous les monstres de base et leurs taux d'invocation")
    @GetMapping("/rates")
    public ResponseEntity<List<BaseMonster>> getInvocationRates() {
        return ResponseEntity.ok(baseMonsterRepository.findAll());
    }

    private boolean isUserError(String message) {
        if (message == null) return false;
        String lower = message.toLowerCase();
        return lower.contains("capacité") || lower.contains("maximale") || lower.contains("requis");
    }

    @Operation(summary = "Historique des invocations", description = "Récupère les logs d'invocation d'un utilisateur")
    @GetMapping("/history/{username}")
    public ResponseEntity<List<InvocationLog>> getHistory(@PathVariable String username) {
        return ResponseEntity.ok(invocationLogRepository.findByUsername(username));
    }
}
