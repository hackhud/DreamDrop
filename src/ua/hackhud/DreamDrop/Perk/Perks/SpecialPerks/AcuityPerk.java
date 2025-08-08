package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class AcuityPerk extends SpecialBasedPerk implements Perk {

    private static final int CRIT_DAMAGE_MULTIPLIER = 3;
    private static final int CRIT_DAMAGE_PERCENT_MULTIPLIER = 1;
    private static final String ICON_PATH = "{AcuityDivine}.png";

    public AcuityPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.CRIT_DAMAGE)
                || item.hasAttribute(AttributeType.CRIT_DAMAGE_PERCENT);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        double value = (item.get(AttributeType.CRIT_DAMAGE) / CRIT_DAMAGE_MULTIPLIER) + (item.get(AttributeType.CRIT_DAMAGE_PERCENT) / CRIT_DAMAGE_PERCENT_MULTIPLIER);
        item.set(AttributeType.BOW_DAMAGE_PERCENT, item.get(AttributeType.BOW_DAMAGE_PERCENT) + value);
        item.addIcon(ICON_PATH);
    }
}