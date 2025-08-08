package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class AbsoluteHitPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{AbsolutehitLegendary}.png";

    public AbsoluteHitPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.CRIT_DAMAGE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.replaceAttribute(AttributeType.CRIT_DAMAGE, AttributeType.DAMAGE, item.get(AttributeType.CRIT_DAMAGE));
        item.replaceAttribute(AttributeType.CRIT_DAMAGE_PERCENT, AttributeType.DAMAGE_PERCENT, item.get(AttributeType.CRIT_DAMAGE_PERCENT));
        item.remove(AttributeType.CRIT_CHANCE);
        item.addIcon(ICON_PATH);
    }
}