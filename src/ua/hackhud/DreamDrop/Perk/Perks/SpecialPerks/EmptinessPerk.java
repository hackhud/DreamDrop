package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class EmptinessPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{EmptinessAncient}.png";

    public EmptinessPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.CRIT_DAMAGE);
    }
    @Override
    public void applyTo(RPGItemStack item) {
        item.replaceAttribute(AttributeType.HEALTH, AttributeType.DAMAGE, item.get(AttributeType.HEALTH));
        item.addIcon(ICON_PATH);
    }
}