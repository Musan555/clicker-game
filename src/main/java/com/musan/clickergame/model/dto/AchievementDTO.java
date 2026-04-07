package com.musan.clickergame.model.dto;

import com.musan.clickergame.model.Achievement;

public class AchievementDTO {

    private String name;        // Nombre del logro
    private String description; // Descripción del logro
    private boolean unlocked;   // Si el jugador lo ha desbloqueado

    // Constructor que recibe un Achievement y saca los datos
    public AchievementDTO(Achievement achievement) {
        this.name = achievement.getType().getName();
        this.description = achievement.getType().getDescription();
        this.unlocked = achievement.isUnlocked();
    }

    // Getters para poder acceder desde el frontend
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUnlocked() {
        return unlocked;
    }
}