package com.musan.clickergame.model;

public class Skill {

    private String id;
    private String name;
    private String description;

    private int level;
    private int maxLevel;
    private int prestigeCost;

    // ---------- CONSTRUCTOR ----------
    public Skill(String id, String name, String description, int maxLevel, int prestigeCost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = 0;
        this.maxLevel = maxLevel;
        this.prestigeCost = prestigeCost;
    }

    // ---------- LÓGICA ----------
    public boolean canUpgrade(int playerPrestigePoints) {
        return level < maxLevel && playerPrestigePoints >= prestigeCost;
    }

    public void levelUp() {
        if (level < maxLevel) {
            level++;
        }
    }

    // ---------- GETTERS ----------
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getPrestigeCost() {
        return prestigeCost;
    }

    // ---------- SETTERS ----------
    public void setLevel(int level) {
        this.level = level;
    }

}