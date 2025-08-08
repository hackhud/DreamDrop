package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class SunderPerk extends RarityBasedPerk<SunderPerk.SunderRarity> {

    public SunderPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.BOW_DAMAGE) ||
                item.hasAttribute(AttributeType.BOW_DAMAGE_PERCENT);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        SunderRarity rarity = rollRandomRarity(SunderRarity.class);
        int boost = rarity.getBoostPercentage();

        if (item.hasAttribute(AttributeType.BOW_DAMAGE)) {
            item.multiplyPercent(AttributeType.BOW_DAMAGE, boost);
        }
        if (item.hasAttribute(AttributeType.BOW_DAMAGE_PERCENT)) {
            item.multiplyPercent(AttributeType.BOW_DAMAGE_PERCENT, boost);
        }

        item.addIcon(rarity.getIconPath());
    }

    public enum SunderRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{SplitCommon}.png", 50),
        UNCOMMON(15, "{SplitUncommon}.png", 45),
        RARE(20, "{SplitRare}.png", 40),
        EPIC(25, "{SplitEpic}.png", 35),
        MYTHIC(30, "{SplitMythic}.png", 30),
        LEGENDARY(35, "{SplitLegendary}.png", 25),
        DIVINE(40, "{SplitDivine}.png", 20),
        RELIC(45, "{SplitRelic}.png", 15),
        ANCIENT(50, "{SplitAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        SunderRarity(int boostPercentage, String iconPath, int weight) {
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