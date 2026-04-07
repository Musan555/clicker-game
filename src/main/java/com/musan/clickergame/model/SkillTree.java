package com.musan.clickergame.model;

import java.util.ArrayList;
import java.util.List;

public class SkillTree {

    private List<Skill> skills;
    private int availablePrestigePoints;

    // ---------- CONSTRUCTOR ----------
    public SkillTree() {

        skills = new ArrayList<>();

        skills.add(new Skill("click1","Click Boost","+1 coin per click",5,1));
        skills.add(new Skill("click2","Strong Fingers","+2 coins per click",3,2));
        skills.add(new Skill("click3","Mega Click","+5 coins per click",2,3));

        skills.add(new Skill("passive1","Passive Income I","+5 passive coins",3,1));
        skills.add(new Skill("passive2","Passive Income II","+10 passive coins",3,2));
        skills.add(new Skill("passive3","Passive Master","+25 passive coins",2,3));

        skills.add(new Skill("multi1","Golden Touch","+10% coins",2,2));
        skills.add(new Skill("multi2","Coin Multiplier","+20% coins",2,3));

        skills.add(new Skill("discount","Upgrade Discount","Upgrades -10% cost",1,3));

        skills.add(new Skill("ultimate","Ultimate Clicker","+10 coins per click",1,3));
    }

    // ---------- COMPRAR SKILL ----------
    public boolean buySkill(int index) {

        Skill skill = skills.get(index);

        if (!skill.canUpgrade(availablePrestigePoints)) {
            return false;
        }

        availablePrestigePoints -= skill.getPrestigeCost();
        skill.levelUp();

        return true;
    }

    // ---------- GETTERS ----------
    public List<Skill> getSkills() {
        return skills;
    }

    public int getAvailablePrestigePoints() {
        return availablePrestigePoints;
    }

    // ---------- SETTERS ----------
    public void setAvailablePrestigePoints(int availablePrestigePoints) {
        this.availablePrestigePoints = availablePrestigePoints;
    }
}