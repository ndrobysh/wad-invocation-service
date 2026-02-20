package com.wad.invocation.service;

import com.wad.invocation.model.BaseMonster;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class InvocationEngine {

    private final Random random = new Random();

    public BaseMonster generateRandomMonster(List<BaseMonster> availableMonsters) {
        if (availableMonsters == null || availableMonsters.isEmpty()) {
            throw new IllegalStateException("Aucun monstre de base disponible pour l'invocation.");
        }

        double totalRate = availableMonsters.stream()
                .mapToDouble(BaseMonster::getInvocationRate)
                .sum();

        double randomValue = random.nextDouble() * totalRate;
        double currentSum = 0.0;

        for (BaseMonster monster : availableMonsters) {
            currentSum += monster.getInvocationRate();
            if (randomValue <= currentSum) {
                return monster;
            }
        }

        // Fallback in case of rounding errors
        return availableMonsters.get(availableMonsters.size() - 1);
    }
}
