package com.musan.clickergame.model;

import java.util.List;

public class GameState {

    private Player player;
    private Prestige prestige;
    private SkillTree skillTree;
    private List<Achievement> achievements;

    // Constructor
    public GameState(Player player, Prestige prestige, SkillTree skillTree, List<Achievement> achievements) {
        this.player = player;
        this.prestige = prestige;
        this.skillTree = skillTree;
        this.achievements = achievements;
    }

    // Getters
    public Player getPlayer() { return player; }
    public Prestige getPrestige() { return prestige; }
    public SkillTree getSkillTree() { return skillTree; }
    public List<Achievement> getAchievements() { return achievements; }

    // Setters (opcional si quieres modificar directamente)
    public void setPlayer(Player player) { this.player = player; }
    public void setPrestige(Prestige prestige) { this.prestige = prestige; }
    public void setSkillTree(SkillTree skillTree) { this.skillTree = skillTree; }
    public void setAchievements(List<Achievement> achievements) { this.achievements = achievements; }
}
