package com.wad.invocation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "base_monsters")
public class BaseMonster {

    @Id
    private String id;
    private String name;
    private String elementType; // feu / eau / vent

    private int hp;
    private int atk;
    private int def;
    private int vit;

    private double invocationRate; // Example: 0.5 for 50%, 0.1 for 10%

    private List<SkillTemplate> skills;

    public BaseMonster() {}

    public BaseMonster(String id, String name, String elementType, int hp, int atk, int def, int vit, double invocationRate, List<SkillTemplate> skills) {
        this.id = id;
        this.name = name;
        this.elementType = elementType;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.vit = vit;
        this.invocationRate = invocationRate;
        this.skills = skills;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getElementType() { return elementType; }
    public void setElementType(String elementType) { this.elementType = elementType; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getAtk() { return atk; }
    public void setAtk(int atk) { this.atk = atk; }
    public int getDef() { return def; }
    public void setDef(int def) { this.def = def; }
    public int getVit() { return vit; }
    public void setVit(int vit) { this.vit = vit; }
    public double getInvocationRate() { return invocationRate; }
    public void setInvocationRate(double invocationRate) { this.invocationRate = invocationRate; }
    public List<SkillTemplate> getSkills() { return skills; }
    public void setSkills(List<SkillTemplate> skills) { this.skills = skills; }
}
