package com.musan.clickergame.model;

public class Achievement {

    private AchievementType type;
    private boolean unlocked;

    // Constructor
    public Achievement(AchievementType type) {
        this.type = type;
        this.unlocked = false;
    }

    // Getters
    public AchievementType getType() { return type; }
    public boolean isUnlocked() { return unlocked; }

    // Desbloquear logro
    public void unlock() { this.unlocked = true; }
}