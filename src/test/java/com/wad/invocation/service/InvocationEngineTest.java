package com.wad.invocation.service;

import com.wad.invocation.model.BaseMonster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InvocationEngineTest {

    private InvocationEngine invocationEngine;
    private List<BaseMonster> availableMonsters;

    @BeforeEach
    void setUp() {
        invocationEngine = new InvocationEngine();
        availableMonsters = new ArrayList<>();

        availableMonsters.add(new BaseMonster("1", "Slime", "eau", 10, 10, 10, 10, 0.60, null)); // 60%
        availableMonsters.add(new BaseMonster("2", "Fenix", "feu", 10, 10, 10, 10, 0.20, null)); // 20%
        availableMonsters.add(new BaseMonster("3", "Golem", "terre", 10, 10, 10, 10, 0.15, null)); // 15%
        availableMonsters.add(new BaseMonster("4", "Dragon", "feu", 10, 10, 10, 10, 0.05, null)); // 5%
    }

    @Test
    void testInvocationDistribution() {
        int totalInvocations = 75000;
        Map<String, Integer> results = new HashMap<>();

        for (BaseMonster m : availableMonsters) {
            results.put(m.getName(), 0);
        }

        for (int i = 0; i < totalInvocations; i++) {
            BaseMonster summoned = invocationEngine.generateRandomMonster(availableMonsters);
            results.put(summoned.getName(), results.get(summoned.getName()) + 1);
        }

        for (BaseMonster m : availableMonsters) {
            double expectedRate = m.getInvocationRate();
            double actualRate = (double) results.get(m.getName()) / totalInvocations;
            double margin = 0.02; // 2% de marge

            System.out.printf("%s: attendu=%.1f%% reel=%.1f%%%n",
                    m.getName(), expectedRate * 100, actualRate * 100);

            assertTrue(Math.abs(expectedRate - actualRate) <= margin,
                    m.getName() + " hors limite: " + expectedRate + " vs " + actualRate);
        }
    }
}
