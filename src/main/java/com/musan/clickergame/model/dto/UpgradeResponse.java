package com.musan.clickergame.model.dto;

public class UpgradeResponse {

    private String name;
    private String description;
    private int level;
    private int maxLevel;
    private long cost;
    private long effectValue;

    public UpgradeResponse(String name,
                           String description,
                           int level,
                           int maxLevel,
                           long cost,
                           long effectValue) {

        this.name = name;
        this.description = description;
        this.level = level;
        this.maxLevel = maxLevel;
        this.cost = cost;
        this.effectValue = effectValue;
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

    public long getCost() {
        return cost;
    }

    public long getEffectValue() {
        return effectValue;
    }
}