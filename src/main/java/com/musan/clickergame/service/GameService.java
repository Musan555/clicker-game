package com.musan.clickergame.service;

import com.musan.clickergame.model.*;
import com.musan.clickergame.model.dto.AchievementDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final Player player;
    private final UpgradeService upgradeService;

    private long lastUpdateTime;

    public GameService(UpgradeService upgradeService, PlayerService playerService) {
        this.player = playerService.getPlayer();
        this.upgradeService = upgradeService;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    // ---------- CLICK ----------
    public void click() {
        updatePassive();
        player.addCoins(1);

        checkAutoPrestigeProgress();
        checkAchievements();
    }

    // ---------- PLAYER ----------
    public Player getPlayerData() {
        updatePassive();
        checkAchievements();
        return player;
    }

    // ---------- PASIVO ----------
    private void updatePassive() {

        long now = System.currentTimeMillis();
        long elapsedMillis = now - lastUpdateTime;

        if (elapsedMillis <= 0) return;

        long seconds = elapsedMillis / 1000;
        if (seconds <= 0) return;

        long passive = player.getPassiveBonus();
        long gained = passive * seconds;

        if (gained > 0) {
            player.setCurrentCoins(player.getCurrentCoins() + gained);
            player.setTotalCoinsEarned(player.getTotalCoinsEarned() + gained);
            player.setCoinsThisRun(player.getCoinsThisRun() + gained);

            checkAutoPrestigeProgress();
            checkAchievements();
        }

        lastUpdateTime = now;
    }

    // ---------- AUTO PRESTIGE ----------
    private void checkAutoPrestigeProgress() {

        long coins = player.getCoinsThisRun();
        long requirement = player.getPrestigeRequirement();

        int gainedPoints = 0;

        while (coins >= requirement) {
            coins -= requirement;
            requirement *= 2;
            gainedPoints++;
        }

        if (gainedPoints > 0) {
            player.setCoinsThisRun(coins);
            player.setPrestigeRequirement(requirement);
            player.setPrestigePoints(player.getPrestigePoints() + gainedPoints);

            player.getSkillTree().setAvailablePrestigePoints(
                    player.getSkillTree().getAvailablePrestigePoints() + (gainedPoints * 2)
            );
        }
    }

    // ---------- UPGRADES ----------
    public List<Upgrade> getUpgrades() {
        return upgradeService.getUpgrades();
    }

    public boolean buyUpgrade(int index) {
        updatePassive();
        Upgrade upgrade = upgradeService.getUpgrades().get(index);
        boolean bought = upgradeService.purchaseUpgrade(player, upgrade);
        checkAchievements();
        return bought;
    }

    // ---------- PRESTIGE ----------
    public boolean prestige() {
        updatePassive();
        applyPrestige(false);
        checkAchievements();
        return true;
    }

    private void applyPrestige(boolean gainedPoint) {
        player.setResetsCount(player.getResetsCount() + 1);
        player.resetForPrestige();
        upgradeService.resetAllUpgrades(player);
    }

    // ---------- SKILLS ----------
    public boolean buySkill(int index) {
        updatePassive();

        boolean success = player.getSkillTree().buySkill(index);
        if (!success) return false;

        Skill skill = player.getSkillTree().getSkills().get(index);
        applySkillEffect(skill);
        checkAchievements();

        return true;
    }

    private void applySkillEffect(Skill skill) {
        switch (skill.getId()) {
            case "click1": player.setCoinsPerClick(player.getCoinsPerClick() + 1); break;
            case "click2": player.setCoinsPerClick(player.getCoinsPerClick() + 2); break;
            case "click3": player.setCoinsPerClick(player.getCoinsPerClick() + 5); break;
            case "ultimate": player.setCoinsPerClick(player.getCoinsPerClick() + 10); break;
            case "passive1": player.setPassiveBonus(player.getPassiveBonus() + 5); break;
            case "passive2": player.setPassiveBonus(player.getPassiveBonus() + 10); break;
            case "passive3": player.setPassiveBonus(player.getPassiveBonus() + 25); break;
        }
    }

    // ---------- LOGROS ----------
    private void checkAchievements() {
        for (Achievement a : player.getAchievements()) {
            if (!a.isUnlocked()) {
                switch (a.getType()) {
                    case FIRST_CLICK:
                        if (player.getTotalCoinsEarned() >= 1) a.unlock();
                        break;
                    case TEN_THOUSAND_COINS:
                        if (player.getTotalCoinsEarned() >= 10_000) a.unlock();
                        break;
                    case HUNDRED_THOUSAND_COINS:
                        if (player.getTotalCoinsEarned() >= 100_000) a.unlock();
                        break;
                    case MILLION_COINS:
                        if (player.getTotalCoinsEarned() >= 1_000_000) a.unlock();
                        break;
                    case FIVE_MILLION_COINS:
                        if (player.getTotalCoinsEarned() >= 5_000_000) a.unlock();
                        break;
                    case TEN_MILLION_COINS:
                        if (player.getTotalCoinsEarned() >= 10_000_000) a.unlock();
                        break;
                    case FIRST_PRESTIGE_POINT:
                        if (player.getPrestigePoints() >= 1) a.unlock();
                        break;
                    case UPGRADE_MASTER:
                        if (player.getTotalUpgradesBought() >= 50) a.unlock();
                        break;
                    case SKILL_TREE_BEGINNER:
                        if (player.getSkillTree().getSkills().size() >= 1) a.unlock();
                        break;
                    case SKILL_TREE_EXPERT:
                        if (player.getSkillTree().getSkills().stream()
                                .allMatch(s -> s.getLevel() >= s.getMaxLevel())) {
                            a.unlock();
                        }
                        break;
                }
            }
        }
    }

    // ✅ Devuelve logros listos para JS (name, description, unlocked)
    public List<AchievementDTO> getAchievements() {
        return player.getAchievements().stream()
                .map(AchievementDTO::new)
                .toList();
    }

}