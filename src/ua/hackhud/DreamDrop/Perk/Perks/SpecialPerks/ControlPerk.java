package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class ControlPerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{ControlLegendary}.png";

    public ControlPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.EVASION);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        item.replaceAttribute(AttributeType.EVASION, AttributeType.TOTAL_ANTI_DMG_CHANCE, item.get(AttributeType.EVASION));
        item.addIcon(ICON_PATH);
    }
}