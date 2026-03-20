package com.wad.invocation.service;

import com.wad.invocation.client.AuthClient;
import com.wad.invocation.client.MonsterClient;
import com.wad.invocation.client.PlayerClient;
import com.wad.invocation.dto.CreateMonsterRequest;
import com.wad.invocation.dto.InvocationResponse;
import com.wad.invocation.model.BaseMonster;
import com.wad.invocation.model.InvocationLog;
import com.wad.invocation.model.InvocationStatus;
import com.wad.invocation.model.SkillTemplate;
import com.wad.invocation.repository.BaseMonsterRepository;
import com.wad.invocation.repository.InvocationLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvocationService {

    private static final Logger log = LoggerFactory.getLogger(InvocationService.class);

    private final BaseMonsterRepository baseMonsterRepository;
    private final InvocationLogRepository invocationLogRepository;
    private final InvocationEngine invocationEngine;

    private final AuthClient authClient;
    private final MonsterClient monsterClient;
    private final PlayerClient playerClient;

    public InvocationService(BaseMonsterRepository baseMonsterRepository, InvocationLogRepository invocationLogRepository,
                             InvocationEngine invocationEngine, AuthClient authClient, MonsterClient monsterClient, PlayerClient playerClient) {
        this.baseMonsterRepository = baseMonsterRepository;
        this.invocationLogRepository = invocationLogRepository;
        this.invocationEngine = invocationEngine;
        this.authClient = authClient;
        this.monsterClient = monsterClient;
        this.playerClient = playerClient;
    }

    public InvocationResponse invokeMonster(String authHeader) {
        // 1. Validate Token
        String username = authClient.validateToken(authHeader);
        if (username == null) {
            return new InvocationResponse(null, "UNAUTHORIZED", null, null, "Token invalide ou expiré");
        }

        // 2. Select Random BaseMonster
        List<BaseMonster> baseMonsters = baseMonsterRepository.findAll();
        BaseMonster selectedMonster = invocationEngine.generateRandomMonster(baseMonsters);

        // 3. Create InvocationLog (PENDING)
        InvocationLog invocationLog = new InvocationLog(
                null, username, selectedMonster.getId(), null, InvocationStatus.PENDING, LocalDateTime.now(), null
        );
        invocationLog = invocationLogRepository.save(invocationLog);

        return processInvocation(invocationLog, selectedMonster, authHeader, username);
    }

    public InvocationResponse recreateFailedInvocation(String logId, String authHeader) {
        InvocationLog logEntry = invocationLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("InvocationLog non trouvé"));

        if (logEntry.getStatus() == InvocationStatus.COMPLETED) {
            return new InvocationResponse(logId, logEntry.getStatus().name(), null, null, "Invocation déjà terminée");
        }

        BaseMonster selectedMonster = baseMonsterRepository.findById(logEntry.getBaseMonsterId())
                .orElseThrow(() -> new IllegalStateException("BaseMonster non trouvé pour la recréation"));

        String username = authClient.validateToken(authHeader);
        if (username == null || !username.equals(logEntry.getUsername())) {
            return new InvocationResponse(null, "UNAUTHORIZED", null, null, "Token invalide ou ne correspond pas au joueur de l'invocation");
        }

        return processInvocation(logEntry, selectedMonster, authHeader, username);
    }

    private InvocationResponse processInvocation(InvocationLog logEntry, BaseMonster selectedMonster, String token, String username) {
        try {
            // System.out.println("debug invoc: " + logEntry.getId() + " / " + selectedMonster.getName());
            // 4. Create Monster in Monster Service if not already done
            if (logEntry.getStatus() == InvocationStatus.PENDING || logEntry.getGeneratedMonsterId() == null) {
                CreateMonsterRequest request = buildCreateRequest(selectedMonster, username);
                String generatedId = monsterClient.createMonster(token, request);

                if (generatedId == null) {
                    logEntry.setStatus(InvocationStatus.FAILED);
                    logEntry.setErrorMessage("Échec de la création du monstre dans le service Monster");
                    invocationLogRepository.save(logEntry);
                    return buildResponse(logEntry, selectedMonster, "Échec de l'étape 1: Création du monstre");
                }

                logEntry.setGeneratedMonsterId(generatedId);
                logEntry.setStatus(InvocationStatus.MONSTER_CREATED);
                invocationLogRepository.save(logEntry);
            }

            // 5. Add Monster to Player in Player Service
            if (logEntry.getStatus() == InvocationStatus.MONSTER_CREATED) {
                boolean added = playerClient.addMonsterToPlayer(token, logEntry.getGeneratedMonsterId());
                if (!added) {
                    logEntry.setStatus(InvocationStatus.FAILED);
                    logEntry.setErrorMessage("Échec de l'ajout du monstre dans le service Player");
                    invocationLogRepository.save(logEntry);
                    return buildResponse(logEntry, selectedMonster, "Échec de l'étape 2: Ajout au joueur");
                }

                logEntry.setStatus(InvocationStatus.COMPLETED);
                logEntry.setErrorMessage(null);
                invocationLogRepository.save(logEntry);
            }

            return buildResponse(logEntry, selectedMonster, "Invocation réussie !");

        } catch (Exception e) {
            log.error("Erreur inattendue pendant l'invocation", e);
            logEntry.setStatus(InvocationStatus.FAILED);
            logEntry.setErrorMessage(e.getMessage());
            invocationLogRepository.save(logEntry);
            return buildResponse(logEntry, selectedMonster, "Erreur système lors de l'invocation");
        }
    }

    private CreateMonsterRequest buildCreateRequest(BaseMonster base, String owner) {
        List<SkillTemplate> templates = base.getSkills();
        List<CreateMonsterRequest.SkillDto> skillDtos = new ArrayList<>();
        for (int i = 0; i < templates.size(); i++) {
            SkillTemplate s = templates.get(i);
            skillDtos.add(new CreateMonsterRequest.SkillDto(
                    s.getName(),
                    i + 1,
                    s.getBaseDamage(),
                    s.getCooldown(),
                    1,
                    5,
                    new CreateMonsterRequest.SkillDto.RatioDto(s.getScalingStat(), s.getScalingRatio() * 100)
            ));
        }

        return new CreateMonsterRequest(
                base.getId() != null ? base.getId().hashCode() : 1,
                base.getName(),
                base.getElementType(),
                base.getHp(),
                base.getAtk(),
                base.getDef(),
                base.getVit(),
                skillDtos,
                owner
        );
    }

    private InvocationResponse buildResponse(InvocationLog logEntry, BaseMonster base, String msg) {
        return new InvocationResponse(
                logEntry.getId(),
                logEntry.getStatus().name(),
                base,
                logEntry.getGeneratedMonsterId(),
                msg
        );
    }
}
