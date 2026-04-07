package com.musan.clickergame.model;

public class Upgrade {

    private final UpgradeType type;
    private final UpgradeCategory category;

    private final String name;
    private final String description;

    private int level;
    private final int maxLevel;

    private final long baseCost;
    private final long effectValue;

    private final double costMultiplier;
    private final int requiredPreviousLevel;

    public Upgrade(
            UpgradeType type,
            String name,
            String description,
            int requiredPreviousLevel
    ) {
        this.type = type;
        this.category = type.getCategory();

        this.name = name;
        this.description = description;
        this.requiredPreviousLevel = requiredPreviousLevel;

        this.level = 0;
        this.maxLevel = 100;

        this.baseCost = type.getBaseCost();
        this.effectValue = type.getEffectValue();

        this.costMultiplier = 1.15;
    }

    // ---------- LEVEL ----------
    public void levelUp() {
        if (level < maxLevel) {
            level++;
        }
    }

    // 🔥 NUEVO: RESET PARA PRESTIGE
    public void reset() {
        this.level = 0;
    }

    // ---------- GETTERS ----------
    public UpgradeType getType() {
        return type;
    }

    public UpgradeCategory getCategory() {
        return category;
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

    public long getBaseCost() {
        return baseCost;
    }

    public long getEffectValue() {
        return effectValue;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }

    public int getRequiredPreviousLevel() {
        return requiredPreviousLevel;
    }
}
