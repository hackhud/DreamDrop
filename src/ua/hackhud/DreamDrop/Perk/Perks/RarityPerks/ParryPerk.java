package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class ParryPerk extends RarityBasedPerk<ParryPerk.ParryRarity> {

    public ParryPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.EVASION);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        ParryRarity rarity = rollRandomRarity(ParryRarity.class);
        item.multiplyPercent(AttributeType.EVASION, rarity.getBoostPercentage());
        item.addIcon(rarity.getIconPath());
    }

    public enum ParryRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{ParryingCommon}.png", 50),
        UNCOMMON(15, "{ParryingUncommon}.png", 45),
        RARE(20, "{ParryingRare}.png", 40),
        EPIC(25, "{ParryingEpic}.png", 35),
        MYTHIC(30, "{ParryingMythic}.png", 30),
        LEGENDARY(35, "{ParryingLegendary}.png", 25),
        DIVINE(40, "{ParryingDivine}.png", 20),
        RELIC(45, "{ParryingRelic}.png", 15),
        ANCIENT(50, "{ParryingAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        ParryRarity(int boostPercentage, String iconPath, int weight) {
            this.boostPercentage = boostPercentage;
            this.iconPath = iconPath;
            this.weight = weight;
        }

        public int getBoostPercentage() {
            return boostPercentage;
        }

        @Override
        public String getIconPath() {
            return iconPath;
        }

        @Override
        public int getWeight() {
            return weight;
        }
    }
}