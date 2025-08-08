package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class RegenPerk extends RarityBasedPerk<RegenPerk.RegenRarity> {

    public RegenPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.REGEN);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        RegenRarity rarity = rollRandomRarity(RegenRarity.class);
        int boost = rarity.getBoostPercentage();

        item.multiplyPercent(AttributeType.REGEN, boost);
        item.multiplyPercent(AttributeType.REGEN_PERCENT, boost);
        item.addIcon(rarity.getIconPath());
    }

    public enum RegenRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{RegenerationCommon}.png", 50),
        UNCOMMON(15, "{RegenerationUncommon}.png", 45),
        RARE(20, "{RegenerationRare}.png", 40),
        EPIC(25, "{RegenerationEpic}.png", 35),
        MYTHIC(30, "{RegenerationMythic}.png", 30),
        LEGENDARY(35, "{RegenerationLegendary}.png", 25),
        DIVINE(40, "{RegenerationDivine}.png", 20),
        RELIC(45, "{RegenerationRelic}.png", 15),
        ANCIENT(50, "{RegenerationAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        RegenRarity(int boostPercentage, String iconPath, int weight) {
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