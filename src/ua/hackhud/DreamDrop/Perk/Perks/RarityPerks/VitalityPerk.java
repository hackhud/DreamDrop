package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class VitalityPerk extends RarityBasedPerk<VitalityPerk.VitalityRarity> {

    public VitalityPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.HEALTH);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        VitalityRarity rarity = rollRandomRarity(VitalityRarity.class);
        item.multiplyPercent(AttributeType.HEALTH, rarity.getBoostPercentage());
        item.addIcon(rarity.getIconPath());
    }

    public enum VitalityRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{SurvivabilityCommon}.png", 50),
        UNCOMMON(15, "{SurvivabilityUncommon}.png", 45),
        RARE(20, "{SurvivabilityRare}.png", 40),
        EPIC(25, "{SurvivabilityEpic}.png", 35),
        MYTHIC(30, "{SurvivabilityMythic}.png", 30),
        LEGENDARY(35, "{SurvivabilityLegendary}.png", 25),
        DIVINE(40, "{SurvivabilityDivine}.png", 20),
        RELIC(45, "{SurvivabilityRelic}.png", 15),
        ANCIENT(50, "{SurvivabilityAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        VitalityRarity(int boostPercentage, String iconPath, int weight) {
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