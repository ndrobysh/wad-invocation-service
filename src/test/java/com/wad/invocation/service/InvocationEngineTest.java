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
        int totalInvocations = 100000;
        Map<String, Integer> results = new HashMap<>();

        // Initialize counters
        for (BaseMonster m : availableMonsters) {
            results.put(m.getName(), 0);
        }

        // Perform invocations
        for (int i = 0; i < totalInvocations; i++) {
            BaseMonster summoned = invocationEngine.generateRandomMonster(availableMonsters);
            results.put(summoned.getName(), results.get(summoned.getName()) + 1);
        }

        // Validate distribution
        for (BaseMonster m : availableMonsters) {
            double expectedRate = m.getInvocationRate();
            double actualRate = (double) results.get(m.getName()) / totalInvocations;

            // Allow a 1% margin of error (0.01 absolute difference)
            double marginOfError = 0.01;

            System.out.printf("Monster: %s | Expected: %.2f%% | Actual: %.2f%%%n",
                    m.getName(), expectedRate * 100, actualRate * 100);

            assertTrue(Math.abs(expectedRate - actualRate) <= marginOfError,
                    "Taux d'invocation pour " + m.getName() + " hors limite : attendu " + expectedRate + ", obtenu " + actualRate);
        }
    }
}
