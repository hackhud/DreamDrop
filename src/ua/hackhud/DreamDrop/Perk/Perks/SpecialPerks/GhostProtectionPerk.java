package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class GhostProtectionPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{GhostprotectionRelic}.png";
    private static final int amountNeeded = 4;

    public GhostProtectionPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.ARMOR);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.set(AttributeType.BLOCK_DAMAGE, item.get(AttributeType.ARMOR) / amountNeeded);
        item.addIcon(ICON_PATH);
    }
}