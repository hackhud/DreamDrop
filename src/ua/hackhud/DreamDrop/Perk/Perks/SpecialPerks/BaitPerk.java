package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class BaitPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{BaitAncient}.png";
    private static final int speedAdded = 15;
    private static final int evasionAdded = 10;

    public BaitPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.ARMOR);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.remove(AttributeType.ARMOR);
        item.set(AttributeType.SPEED, item.get(AttributeType.SPEED) + speedAdded);
        item.set(AttributeType.EVASION, item.get(AttributeType.EVASION) + evasionAdded);
        item.addIcon(ICON_PATH);
    }
}