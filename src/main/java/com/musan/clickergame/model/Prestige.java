package com.musan.clickergame.model;

public class Prestige {

    private int points;                 // puntos de prestigio permanentes
    private long currentProgress;       // barra de progreso actual
    private long nextPointRequirement;  // monedas necesarias para el siguiente punto
    private int totalPrestigeEarned;    // opcional, total histórico

    // Constructor inicial
    public Prestige() {
        this.points = 0;
        this.currentProgress = 0;
        this.nextPointRequirement = 2_000_000; // primera run absurdamente cara
        this.totalPrestigeEarned = 0;
    }

    // Getters
    public int getPoints() {
        return points;
    }

    public long getCurrentProgress() {
        return currentProgress;
    }

    public long getNextPointRequirement() {
        return nextPointRequirement;
    }

    public int getTotalPrestigeEarned() {
        return totalPrestigeEarned;
    }

}
