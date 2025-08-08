package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class AbsorptionPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{AbsorptionDivine}.png";

    public AbsorptionPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.HEALTH)
                && item.hasAttribute(AttributeType.REGEN);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.set(AttributeType.HEALTH, item.get(AttributeType.HEALTH) + item.get(AttributeType.REGEN));
        if (item.hasAttribute(AttributeType.REGEN_PERCENT)) {
            item.set(AttributeType.HEALTH_PERCENT, item.get(AttributeType.HEALTH_PERCENT) + item.get(AttributeType.REGEN_PERCENT));
        }
        item.remove(AttributeType.REGEN);
        item.remove(AttributeType.REGEN_PERCENT);
        item.addIcon(ICON_PATH);
    }
}