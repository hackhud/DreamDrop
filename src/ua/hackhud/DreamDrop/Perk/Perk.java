package ua.hackhud.DreamDrop.Perk;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;

public interface Perk {
    boolean canApplyTo(RPGItemStack item);
    void applyTo(RPGItemStack item);
    int getBaseWeight();
    String getId();
}
