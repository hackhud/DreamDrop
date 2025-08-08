package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class IntegrityPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{IntegrityAncient}.png";

    public IntegrityPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.PVE_DAMAGE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.replaceAttribute(AttributeType.PVE_DAMAGE, AttributeType.CLEAN_PVE_DAMAGE, item.get(AttributeType.PVE_DAMAGE));
        item.addIcon(ICON_PATH);
    }
}