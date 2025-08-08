package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class PowerfulHitPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{PowerfulhitAncient}.png";
    private static final double MODIFIER = 2;

    public PowerfulHitPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.CRIT_DAMAGE)
                && item.hasAttribute(AttributeType.CRIT_CHANCE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.set(AttributeType.CRIT_DAMAGE, item.get(AttributeType.CRIT_DAMAGE) * MODIFIER);
        item.set(AttributeType.CRIT_CHANCE, item.get(AttributeType.CRIT_CHANCE) / MODIFIER);
        item.addIcon(ICON_PATH);
    }
}