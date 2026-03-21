package com.wad.invocation.dto;

import com.wad.invocation.model.BaseMonster;

// test avec un record java au lieu d'une classe classique
public record InvocationResponse(
        String invocationLogId,
        String status,
        BaseMonster baseMonster,
        String generatedMonsterId,
        String message
) {}
