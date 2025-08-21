package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class TacticlePerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{TacticleLegendary}.png";
    private static final int multiplier = 2;

    public TacticlePerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.BOW_DAMAGE_PERCENT)
                || item.hasAttribute(AttributeType.BOW_DAMAGE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.set(AttributeType.BOW_DAMAGE_PERCENT, item.get(AttributeType.BOW_DAMAGE_PERCENT) * multiplier);
        item.set(AttributeType.BOW_DAMAGE, item.get(AttributeType.BOW_DAMAGE) * multiplier);
        item.set(AttributeType.HEALTH, item.get(AttributeType.HEALTH) / multiplier);
        item.set(AttributeType.REGEN, item.get(AttributeType.REGEN) / multiplier);
        item.addIcon(ICON_PATH);
    }
}