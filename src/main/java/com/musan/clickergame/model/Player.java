package com.musan.clickergame.model;

import com.musan.clickergame.model.SkillTree;
import com.musan.clickergame.model.Upgrade;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Player {

    // Monedas
    private long currentCoins;
    private long totalCoinsEarned;

    // Monedas de la run actual
    private long coinsThisRun;

    // Estadísticas
    private long totalClicks;

    // Prestigio
    private int prestigePoints;
    private long prestigeProgress;
    private long prestigeRequirement;

    // Runs
    private int resetsCount;

    // Mejora de juego
    private long coinsPerClick;
    private long passiveBonus;

    // Skill Tree
    private SkillTree skillTree;

    // Upgrades comprados
    private Map<Upgrade, Integer> upgrades = new HashMap<>();

    // 🔥 LOGROS
    private List<Achievement> achievements = new ArrayList<>();

    // Constructor
    public Player() {
        this.currentCoins = 0;
        this.totalCoinsEarned = 0;
        this.coinsThisRun = 0;

        this.totalClicks = 0;
        this.prestigePoints = 0;
        this.prestigeProgress = 0;
        this.prestigeRequirement = 2_000_000;
        this.resetsCount = 0;

        this.coinsPerClick = 1;
        this.passiveBonus = 0;

        this.skillTree = new SkillTree();

        // 🔥 INICIALIZAR LOGROS
        for (AchievementType type : AchievementType.values()) {
            achievements.add(new Achievement(type));
        }
    }

    // --- LÓGICA DE JUEGO ---

    public void addCoins(long clicks) {
        long gained = clicks * coinsPerClick;

        this.currentCoins += gained;
        this.totalCoinsEarned += gained;
        this.coinsThisRun += gained;
        this.totalClicks += clicks;
    }

    // ---------- UPGRADES ----------

    public void addUpgrade(Upgrade upgrade) {
        upgrades.put(upgrade, upgrades.getOrDefault(upgrade, 0) + 1);
    }

    public int getTotalUpgradesBought() {
        return upgrades.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Map<Upgrade, Integer> getUpgradesMap() {
        return upgrades;
    }

    // ---------- LOGROS ----------

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    // --- GETTERS Y SETTERS ---

    public long getCurrentCoins() { return currentCoins; }
    public void setCurrentCoins(long currentCoins) { this.currentCoins = currentCoins; }

    public long getTotalCoinsEarned() { return totalCoinsEarned; }
    public void setTotalCoinsEarned(long totalCoinsEarned) { this.totalCoinsEarned = totalCoinsEarned; }

    public long getCoinsThisRun() { return coinsThisRun; }
    public void setCoinsThisRun(long coinsThisRun) { this.coinsThisRun = coinsThisRun; }

    public long getTotalClicks() { return totalClicks; }
    public void setTotalClicks(long totalClicks) { this.totalClicks = totalClicks; }

    public int getPrestigePoints() { return prestigePoints; }
    public void setPrestigePoints(int prestigePoints) { this.prestigePoints = prestigePoints; }

    public long getPrestigeProgress() { return prestigeProgress; }
    public void setPrestigeProgress(long prestigeProgress) { this.prestigeProgress = prestigeProgress; }

    public long getPrestigeRequirement() { return prestigeRequirement; }
    public void setPrestigeRequirement(long prestigeRequirement) { this.prestigeRequirement = prestigeRequirement; }

    public int getResetsCount() { return resetsCount; }
    public void setResetsCount(int resetsCount) { this.resetsCount = resetsCount; }

    public long getCoinsPerClick() { return coinsPerClick; }
    public void setCoinsPerClick(long coinsPerClick) { this.coinsPerClick = coinsPerClick; }

    public long getPassiveBonus() { return passiveBonus; }
    public void setPassiveBonus(long passiveBonus) { this.passiveBonus = passiveBonus; }

    public SkillTree getSkillTree() { return skillTree; }
    public void setSkillTree(SkillTree skillTree) { this.skillTree = skillTree; }

    // ---------- PRESTIGE RESET ----------

    public void resetForPrestige() {
        this.currentCoins = 0;
        this.coinsThisRun = 0;
        this.totalClicks = 0;
        this.coinsPerClick = 1;
        this.passiveBonus = 0;
    }
}