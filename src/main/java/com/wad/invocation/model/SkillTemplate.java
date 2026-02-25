package com.wad.invocation.model;

public class SkillTemplate {
    private String name;
    private int baseDamage;
    private int cooldown;
    private String scalingStat; // hp, atk, def, vit
    private double scalingRatio;

    public SkillTemplate() {}

    public SkillTemplate(String name, int baseDamage, int cooldown, String scalingStat, double scalingRatio) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.cooldown = cooldown;
        this.scalingStat = scalingStat;
        this.scalingRatio = scalingRatio;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getBaseDamage() { return baseDamage; }
    public void setBaseDamage(int baseDamage) { this.baseDamage = baseDamage; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown; }
    public String getScalingStat() { return scalingStat; }
    public void setScalingStat(String scalingStat) { this.scalingStat = scalingStat; }
    public double getScalingRatio() { return scalingRatio; }
    public void setScalingRatio(double scalingRatio) { this.scalingRatio = scalingRatio; }
}
