package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Perk.Perk;
import java.util.Random;

public abstract class RarityBasedPerk<T extends Enum<T> & RarityBasedPerk.Rarity> implements Perk {
    protected static final Random RANDOM = new Random();
    protected final int baseWeight;
    protected final String perkId;

    protected RarityBasedPerk(int baseWeight, String perkId) {
        this.baseWeight = baseWeight;
        this.perkId = perkId;
    }

    @Override
    public int getBaseWeight() {
        return baseWeight;
    }

    @Override
    public String getId() {
        return perkId;
    }

    protected T rollRandomRarity(Class<T> rarityClass) {
        T[] values = rarityClass.getEnumConstants();
        int totalWeight = 0;
        for (T rarity : values) {
            totalWeight += rarity.getWeight();
        }

        int roll = RANDOM.nextInt(totalWeight);
        for (T rarity : values) {
            if (roll < rarity.getWeight()) {
                return rarity;
            }
            roll -= rarity.getWeight();
        }

        throw new IllegalStateException("Rarity selection failed for " + rarityClass.getSimpleName());
    }

    public interface Rarity {
        int getWeight();
        String getIconPath();
    }
}