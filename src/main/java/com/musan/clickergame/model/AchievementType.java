package com.musan.clickergame.model;

public enum AchievementType {

    FIRST_CLICK("first_click", "Primer Click", "Has hecho tu primer click"),
    TEN_THOUSAND_COINS("10k_coins", "Curioso del dinero", "Has alcanzado 10.000 monedas"),
    MILLION_COINS("1m_coins", "Millonario", "Has alcanzado 1.000.000 monedas"),
    BILLION_COINS("1b_coins", "Empresario loco", "Has alcanzado 1.000.000.000 monedas"),
    TRILLION_COINS("1t_coins", "Tocando el cielo", "Has alcanzado 1.000.000.000.000 monedas"),
    QUADRILLION_COINS("1q_coins", "Rompe-economías", "Has alcanzado 1.000.000.000.000.000 monedas"),
    FIRST_PRESTIGE_POINT("first_prestige", "Prestigioso", "Has obtenido tu primer punto de prestigio"),
    UPGRADE_BEGINNER("upgrade_beginner", "Manitas", "Has comprado 1 mejora"),
    UPGRADE_HALF("upgrade_half", "Mitad del camino", "Has comprado el 50% de las mejoras"),
    UPGRADE_MASTER("upgrade_master", "Maestro de mejoras", "Has mejorado todas las upgrades al máximo"),
    SKILL_TREE_BEGINNER("skill_tree_beginner", "Aprendiz arcano", "Has desbloqueado tu primera skill"),
    SKILL_TREE_EXPERT("skill_tree_expert", "Maestro del árbol", "Has completado todas las skills del árbol");

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
