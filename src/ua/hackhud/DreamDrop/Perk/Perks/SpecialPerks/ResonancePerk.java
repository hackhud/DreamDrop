package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Entity.AttributeValue;
import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

import java.util.Map;
import java.util.Random;

public class ResonancePerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{ResonanceAncient}.png";

    private static final Random random = new Random();

    public ResonancePerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        for (Map.Entry<AttributeType, AttributeValue> entry : item.getAttributes().entrySet()) {
            AttributeType type = entry.getKey();
            item.set(type, resonanceRoll(item.get(type)));
        }
        item.addIcon(ICON_PATH);
    }

    private double resonanceRoll(double value) {
        double multiplier = 0.5 + random.nextDouble() * 1.5;
        return value * multiplier;
    }
}