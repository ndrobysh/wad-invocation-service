package com.wad.invocation.dto;

import java.util.List;

public class CreateMonsterRequest {
    private int templateId;
    private String name;
    private String elementType;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private List<SkillDto> skills;
    private String owner;

    public CreateMonsterRequest() {}

    public CreateMonsterRequest(int templateId, String name, String elementType, int hp, int atk, int def, int vit, List<SkillDto> skills, String owner) {
        this.templateId = templateId;
        this.name = name;
        this.elementType = elementType;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.vit = vit;
        this.skills = skills;
        this.owner = owner;
    }

    public int getTemplateId() { return templateId; }
    public void setTemplateId(int templateId) { this.templateId = templateId; }
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
    public List<SkillDto> getSkills() { return skills; }
    public void setSkills(List<SkillDto> skills) { this.skills = skills; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public static class SkillDto {
        private String name;
        private int num;
        private int dmg;
        private int cooldown;
        private int level;
        private int lvlMax;
        private RatioDto ratio;

        public SkillDto() {}
        public SkillDto(String name, int num, int dmg, int cooldown, int level, int lvlMax, RatioDto ratio) {
            this.name = name;
            this.num = num;
            this.dmg = dmg;
            this.cooldown = cooldown;
            this.level = level;
            this.lvlMax = lvlMax;
            this.ratio = ratio;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getNum() { return num; }
        public void setNum(int num) { this.num = num; }
        public int getDmg() { return dmg; }
        public void setDmg(int dmg) { this.dmg = dmg; }
        public int getCooldown() { return cooldown; }
        public void setCooldown(int cooldown) { this.cooldown = cooldown; }
        public int getLevel() { return level; }
        public void setLevel(int level) { this.level = level; }
        public int getLvlMax() { return lvlMax; }
        public void setLvlMax(int lvlMax) { this.lvlMax = lvlMax; }
        public RatioDto getRatio() { return ratio; }
        public void setRatio(RatioDto ratio) { this.ratio = ratio; }

        public static class RatioDto {
            private String stat;
            private double percent;

            public RatioDto() {}
            public RatioDto(String stat, double percent) { this.stat = stat; this.percent = percent; }
            public String getStat() { return stat; }
            public void setStat(String stat) { this.stat = stat; }
            public double getPercent() { return percent; }
            public void setPercent(double percent) { this.percent = percent; }
        }
    }
}
