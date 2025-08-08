package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class StabilityPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{StabilityLegendary}.png";

    public StabilityPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.CRIT_CHANCE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.set(AttributeType.CRIT_CHANCE, 100);
        item.addIcon(ICON_PATH);
    }
}