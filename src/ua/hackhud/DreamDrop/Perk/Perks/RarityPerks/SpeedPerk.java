package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class SpeedPerk extends RarityBasedPerk<SpeedPerk.SpeedRarity> {

    public SpeedPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.SPEED);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        SpeedRarity rarity = rollRandomRarity(SpeedRarity.class);
        item.multiplyPercent(AttributeType.SPEED, rarity.getBoostPercentage());
        item.addIcon(rarity.getIconPath());
    }

    public enum SpeedRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{SpeedCommon}.png", 50),
        UNCOMMON(15, "{SpeedUncommon}.png", 45),
        RARE(20, "{SpeedRare}.png", 40),
        EPIC(25, "{SpeedEpic}.png", 35),
        MYTHIC(30, "{SpeedMythic}.png", 30),
        LEGENDARY(35, "{SpeedLegendary}.png", 25),
        DIVINE(40, "{SpeedDivine}.png", 20),
        RELIC(45, "{SpeedRelic}.png", 15),
        ANCIENT(50, "{SpeedAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        SpeedRarity(int boostPercentage, String iconPath, int weight) {
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