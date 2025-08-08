package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;


public abstract class SpecialBasedPerk implements Perk {
    protected final int baseWeight;
    protected final String perkId;

    protected SpecialBasedPerk(int baseWeight, String perkId) {
        this.baseWeight = baseWeight;
        this.perkId = perkId;
    }

    @Override
    public int getBaseWeight() {
        return baseWeight;
    }

    @Override
    public String getId() {
        return perkId;
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }
}