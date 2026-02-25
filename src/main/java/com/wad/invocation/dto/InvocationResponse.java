package com.wad.invocation.dto;

import com.wad.invocation.model.BaseMonster;

public class InvocationResponse {
    private String invocationLogId;
    private String status;
    private BaseMonster baseMonster;
    private String generatedMonsterId;
    private String message;

    public InvocationResponse() {}

    public InvocationResponse(String invocationLogId, String status, BaseMonster baseMonster, String generatedMonsterId, String message) {
        this.invocationLogId = invocationLogId;
        this.status = status;
        this.baseMonster = baseMonster;
        this.generatedMonsterId = generatedMonsterId;
        this.message = message;
    }

    public String getInvocationLogId() { return invocationLogId; }
    public void setInvocationLogId(String invocationLogId) { this.invocationLogId = invocationLogId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BaseMonster getBaseMonster() { return baseMonster; }
    public void setBaseMonster(BaseMonster baseMonster) { this.baseMonster = baseMonster; }
    public String getGeneratedMonsterId() { return generatedMonsterId; }
    public void setGeneratedMonsterId(String generatedMonsterId) { this.generatedMonsterId = generatedMonsterId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
