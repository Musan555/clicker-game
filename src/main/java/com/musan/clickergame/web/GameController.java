package com.musan.clickergame.web;

import com.musan.clickergame.model.Player;
import com.musan.clickergame.model.Skill;
import com.musan.clickergame.model.Upgrade;
import com.musan.clickergame.service.GameService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // ---------- ESTADO ----------
    @GetMapping("/state")
    public Player getState() {
        return gameService.getPlayerData();
    }

    // ---------- CLICK ----------
    @PostMapping("/click")
    public Player click() {
        gameService.click();
        return gameService.getPlayerData();
    }

    // ---------- UPGRADES ----------
    @GetMapping("/upgrades")
    public List<Upgrade> getUpgrades() {
        return gameService.getUpgrades();
    }

    @PostMapping("/upgrade/{index}")
    public boolean buyUpgrade(@PathVariable int index) {
        return gameService.buyUpgrade(index);
    }

    // ---------- PRESTIGE ----------
    @PostMapping("/prestige")
    public boolean prestige() {
        return gameService.prestige();
    }

    // ---------- SKILLS ----------
    @PostMapping("/skill/{index}")
    public boolean buySkill(@PathVariable int index) {
        return gameService.buySkill(index);
    }

    @GetMapping("/skills")
    public List<Skill> getSkills() {
        return gameService.getPlayerData().getSkillTree().getSkills();
    }

}