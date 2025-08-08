package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class ArmadilloPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{ArmadilloLegendary}.png";
    private static final double ARMOR_MODIFIER = 2;
    private static final double EVASION_MINUS = 10;
    private static final double SPEED_MINUS = 10;

    public ArmadilloPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.ARMOR);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.set(AttributeType.ARMOR, item.get(AttributeType.ARMOR) * ARMOR_MODIFIER);
        item.set(AttributeType.SPEED, item.get(AttributeType.SPEED) - SPEED_MINUS);
        item.set(AttributeType.EVASION, item.get(AttributeType.EVASION) - EVASION_MINUS);
        item.addIcon(ICON_PATH);
    }
}