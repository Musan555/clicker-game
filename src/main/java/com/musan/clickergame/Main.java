package com.musan.clickergame;

import com.musan.clickergame.model.*;
import com.musan.clickergame.service.UpgradeService;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Crear jugador, prestigio, skilltree vacío y lista de logros vacía
        Player player = new Player();
        Prestige prestige = new Prestige();
        SkillTree skillTree = new SkillTree();
        ArrayList<Achievement> achievements = new ArrayList<>();

        // GameState
        GameState gameState = new GameState(player, prestige, skillTree, achievements);

        // Servicio de upgrades
        UpgradeService upgradeService = new UpgradeService();

        // Mostrar estado inicial
        System.out.println("Monedas iniciales: " + player.getCurrentCoins());
        System.out.println("Coins per click: " + player.getCoinsPerClick());

        // Simular click
        player.setCurrentCoins(player.getCurrentCoins() + player.getCoinsPerClick());
        System.out.println("Monedas tras un click: " + player.getCurrentCoins());

        // Intentar comprar la primera mejora activa
        Upgrade primeraMejora = upgradeService.getUpgrades().get(0);
        if (upgradeService.purchaseUpgrade(player, primeraMejora)) {
            System.out.println("Mejora comprada: " + primeraMejora.getName());
            System.out.println("Nuevo coins per click: " + player.getCoinsPerClick());
        } else {
            System.out.println("No se puede comprar la mejora: " + primeraMejora.getName());
        }

        // Mostrar coste actual de la siguiente compra
        System.out.println("Coste siguiente nivel: " + upgradeService.calculateCurrentCost(primeraMejora));
    }
}