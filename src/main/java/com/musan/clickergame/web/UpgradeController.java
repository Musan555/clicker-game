package com.musan.clickergame.web;

import com.musan.clickergame.model.Upgrade;
import com.musan.clickergame.model.dto.UpgradeResponse;
import com.musan.clickergame.service.PlayerService;
import com.musan.clickergame.service.UpgradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/upgrades")
public class UpgradeController {

    private final UpgradeService upgradeService;
    private final PlayerService playerService;

    public UpgradeController(
            UpgradeService upgradeService,
            PlayerService playerService
    ) {
        this.upgradeService = upgradeService;
        this.playerService = playerService;
    }

    // 📤 Lista de mejoras + precio actual
    @GetMapping
    public List<UpgradeResponse> getUpgrades() {
        return upgradeService.getUpgrades()
                .stream()
                .map(u -> new UpgradeResponse(
                        u.getName(),
                        u.getDescription(),
                        u.getLevel(),
                        u.getMaxLevel(),
                        upgradeService.calculateCurrentCost(u)
                ))
                .toList();
    }

    // 🛒 Comprar mejora
    @PostMapping("/buy/{index}")
    public boolean buyUpgrade(@PathVariable int index) {

        List<Upgrade> upgrades = upgradeService.getUpgrades();

        if (index < 0 || index >= upgrades.size()) {
            return false;
        }

        return upgradeService.purchaseUpgrade(
                playerService.getPlayer(),
                upgrades.get(index)
        );
    }
}