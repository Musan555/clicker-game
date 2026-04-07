package com.musan.clickergame.model;

public enum AchievementType {

    FIRST_CLICK("first_click", "Primer Click", "Has hecho tu primer click"),
    TEN_THOUSAND_COINS("10k_coins", "Acumulador", "Has alcanzado 10.000 monedas"),
    HUNDRED_THOUSAND_COINS("100k_coins", "Millonario Inicial", "Has alcanzado 100.000 monedas"),
    MILLION_COINS("1m_coins", "Millonario", "Has alcanzado 1.000.000 monedas"),
    FIVE_MILLION_COINS("5m_coins", "Magnate", "Has alcanzado 5.000.000 monedas"),
    TEN_MILLION_COINS("10m_coins", "Tycoon", "Has alcanzado 10.000.000 monedas"),
    FIRST_PRESTIGE_POINT("first_prestige", "Prestigioso", "Has obtenido tu primer punto de prestigio"),
    UPGRADE_MASTER("upgrade_master", "Maestro de mejoras", "Has comprado 50 upgrades"),
    SKILL_TREE_BEGINNER("skill_tree_beginner", "Aprendiz de Habilidades", "Has desbloqueado tu primera skill"),
    SKILL_TREE_EXPERT("skill_tree_expert", "Experto de Habilidades", "Has desbloqueado todas las skills del árbol");

    private final String id;
    private final String name;
    private final String description;

    AchievementType(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
