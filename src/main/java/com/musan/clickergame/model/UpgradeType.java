package com.musan.clickergame.model;

public enum UpgradeType {

    MEJORA_1(UpgradeCategory.ACTIVE, 10, 10000),
    MEJORA_2(UpgradeCategory.PASSIVE, 50, 1),
    MEJORA_3(UpgradeCategory.PASSIVE, 100, 2),
    MEJORA_4(UpgradeCategory.PASSIVE, 250, 3),
    MEJORA_5(UpgradeCategory.PASSIVE, 500, 5),
    MEJORA_6(UpgradeCategory.PASSIVE, 1000, 8),
    MEJORA_7(UpgradeCategory.PASSIVE, 2500, 13),
    MEJORA_8(UpgradeCategory.PASSIVE, 5000, 21),
    MEJORA_9(UpgradeCategory.PASSIVE, 10000, 34),
    MEJORA_10(UpgradeCategory.PASSIVE, 25000, 55);

    private final UpgradeCategory category;
    private final long baseCost;
    private final long effectValue;

    UpgradeType(UpgradeCategory category, long baseCost, long effectValue) {
        this.category = category;
        this.baseCost = baseCost;
        this.effectValue = effectValue;
    }

    public UpgradeCategory getCategory() {
        return category;
    }

    public long getBaseCost() {
        return baseCost;
    }

    public long getEffectValue() {
        return effectValue;
    }
}