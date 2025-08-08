package ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnergyExchangePerk extends SpecialBasedPerk implements Perk {
    private static final String ICON_PATH = "{EnergyexchangeAncient}.png";

    public EnergyExchangePerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.ADDITIONAL_DURABILITY);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        double durabilityBoost = item.get(AttributeType.ADDITIONAL_DURABILITY);
        item.remove(AttributeType.ADDITIONAL_DURABILITY);

        Random random = ThreadLocalRandom.current();
        double percentBoost = 1.0 + (random.nextDouble() * (durabilityBoost - 1.0));
        item.multiplyPercentAll(percentBoost);

        item.addIcon(ICON_PATH);
    }
}