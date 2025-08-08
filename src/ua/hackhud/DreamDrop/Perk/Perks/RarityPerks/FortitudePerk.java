package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class FortitudePerk extends RarityBasedPerk<FortitudePerk.FortitudeRarity> {

    public FortitudePerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.ARMOR) ||
                item.hasAttribute(AttributeType.BLOCK_DAMAGE) ||
                item.hasAttribute(AttributeType.PVE_ARMOR);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        FortitudeRarity rarity = rollRandomRarity(FortitudeRarity.class);
        int boost = rarity.getBoostPercentage();

        if (item.hasAttribute(AttributeType.ARMOR)) {
            item.multiplyPercent(AttributeType.ARMOR, boost);
            item.multiplyPercent(AttributeType.ARMOR_PERCENT, boost);
        }
        if (item.hasAttribute(AttributeType.BLOCK_DAMAGE)) {
            item.multiplyPercent(AttributeType.BLOCK_DAMAGE, boost);
        }
        if (item.hasAttribute(AttributeType.PVE_ARMOR)) {
            item.multiplyPercent(AttributeType.PVE_ARMOR, boost);
        }

        item.addIcon(rarity.getIconPath());
    }

    public enum FortitudeRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{EnduranceCommon}.png", 50),
        UNCOMMON(15, "{EnduranceUncommon}.png", 45),
        RARE(20, "{EnduranceRare}.png", 40),
        EPIC(25, "{EnduranceEpic}.png", 35),
        MYTHIC(30, "{EnduranceMythic}.png", 30),
        LEGENDARY(35, "{EnduranceLegendary}.png", 25),
        DIVINE(40, "{EnduranceDivine}.png", 20),
        RELIC(45, "{EnduranceRelic}.png", 15),
        ANCIENT(50, "{EnduranceAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        FortitudeRarity(int boostPercentage, String iconPath, int weight) {
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