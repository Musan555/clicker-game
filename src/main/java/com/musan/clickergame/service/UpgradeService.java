package com.musan.clickergame.service;

import com.musan.clickergame.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpgradeService {

    private final List<Upgrade> upgrades = new ArrayList<>();

    public UpgradeService() {
        initializeUpgrades();
    }

    private void initializeUpgrades() {
        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_1,
                "Click reforzado",
                "Aumenta las monedas obtenidas por click",
                0
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_2,
                "Generador básico",
                "Genera monedas automáticamente",
                1
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_3,
                "Generador mejorado",
                "Aumenta la generación pasiva",
                5
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_4,
                "Fábrica automática",
                "Produce más monedas por segundo",
                10
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_5,
                "Línea industrial",
                "Gran aumento de producción pasiva",
                15
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_6,
                "Optimización",
                "Mejora la eficiencia global",
                20
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_7,
                "Automatización avanzada",
                "Producción pasiva acelerada",
                25
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_8,
                "IA productiva",
                "Incremento masivo de monedas",
                30
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_9,
                "Red neuronal",
                "Producción exponencial",
                40
        ));

        upgrades.add(new Upgrade(
                UpgradeType.MEJORA_10,
                "Singularidad",
                "Generación absurda de monedas",
                50
        ));
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public long calculateCurrentCost(Upgrade upgrade) {
        return (long) (
                upgrade.getBaseCost()
                        * Math.pow(upgrade.getCostMultiplier(), upgrade.getLevel())
        );
    }

    public boolean canPurchase(Player player, Upgrade upgrade) {
        int index = upgrades.indexOf(upgrade);

        if (index > 0) {
            Upgrade previous = upgrades.get(index - 1);
            if (previous.getLevel() < upgrade.getRequiredPreviousLevel()) {
                return false;
            }
        }

        long cost = calculateCurrentCost(upgrade);
        return player.getCurrentCoins() >= cost
                && upgrade.getLevel() < upgrade.getMaxLevel();
    }

    public boolean purchaseUpgrade(Player player, Upgrade upgrade) {
        if (!canPurchase(player, upgrade)) {
            return false;
        }

        long cost = calculateCurrentCost(upgrade);
        player.setCurrentCoins(player.getCurrentCoins() - cost);

        upgrade.levelUp();

        // 🔥 IMPORTANTE: recalcular TODO después de subir nivel
        applyUpgradeEffect(player);

        return true;
    }

    // NUEVO SISTEMA LIMPIO
    private void applyUpgradeEffect(Player player) {

        long totalClick = 1; // base
        long totalPassive = 0;

        for (Upgrade u : upgrades) {

            if (u.getCategory() == UpgradeCategory.ACTIVE) {
                totalClick += u.getLevel() * u.getEffectValue();
            }

            if (u.getCategory() == UpgradeCategory.PASSIVE) {
                totalPassive += u.getLevel() * u.getEffectValue();
            }
        }

        player.setCoinsPerClick(totalClick);
        player.setPassiveBonus(totalPassive);
    }
    public void resetAllUpgrades(Player player) {

        for (Upgrade u : upgrades) {
            u.reset();
        }

        // 🔥 MUY IMPORTANTE: recalcular stats tras reset
        applyUpgradeEffect(player);
    }
}