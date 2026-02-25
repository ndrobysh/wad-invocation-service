package com.wad.invocation.dto;

public class AddMonsterRequest {
    private String monsterId;

    public AddMonsterRequest() {}

    public AddMonsterRequest(String monsterId) {
        this.monsterId = monsterId;
    }

    public String getMonsterId() { return monsterId; }
    public void setMonsterId(String monsterId) { this.monsterId = monsterId; }
}
