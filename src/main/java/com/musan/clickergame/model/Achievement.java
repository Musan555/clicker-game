package com.musan.clickergame.model;

public class Achievement {

    private AchievementType type;
    private boolean unlocked;

    // 🔥 NECESARIO PARA JACKSON
    public Achievement() {
    }

    // Constructor normal
    public Achievement(AchievementType type) {
        this.type = type;
        this.unlocked = false;
    }

    // Getters
    public AchievementType getType() { return type; }
    public boolean isUnlocked() { return unlocked; }

    // 🔥 SETTERS NECESARIOS
    public void setType(AchievementType type) {
        this.type = type;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    // Desbloquear logro
    public void unlock() {
        this.unlocked = true;
    }

    // Reset
    public void reset() {
        this.unlocked = false;
    }
}