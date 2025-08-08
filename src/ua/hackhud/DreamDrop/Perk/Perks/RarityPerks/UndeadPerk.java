package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class UndeadPerk extends RarityBasedPerk<UndeadPerk.UndeadRarity> {

    public UndeadPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.PVE_DAMAGE) ||
                item.hasAttribute(AttributeType.PVE_DAMAGE_PERCENT) ||
                item.hasAttribute(AttributeType.CLEAN_PVE_DAMAGE) ||
                item.hasAttribute(AttributeType.PVE_CRIT_DAMAGE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        UndeadRarity rarity = rollRandomRarity(UndeadRarity.class);
        int boost = rarity.getBoostPercentage();

        if (item.hasAttribute(AttributeType.PVE_DAMAGE)) {
            item.multiplyPercent(AttributeType.PVE_DAMAGE, boost);
        }
        if (item.hasAttribute(AttributeType.PVE_DAMAGE_PERCENT)) {
            item.multiplyPercent(AttributeType.PVE_DAMAGE_PERCENT, boost);
        }
        if (item.hasAttribute(AttributeType.CLEAN_PVE_DAMAGE)) {
            item.multiplyPercent(AttributeType.CLEAN_PVE_DAMAGE, boost);
        }
        if (item.hasAttribute(AttributeType.PVE_CRIT_DAMAGE)) {
            item.multiplyPercent(AttributeType.PVE_CRIT_DAMAGE, boost);
            item.multiplyPercent(AttributeType.PVE_CRIT_DAMAGE_PERCENT, boost);
        }

        item.addIcon(rarity.getIconPath());
    }

    public enum UndeadRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{UndeadCommon}.png", 50),
        UNCOMMON(15, "{UndeadUncommon}.png", 45),
        RARE(20, "{UndeadRare}.png", 40),
        EPIC(25, "{UndeadEpic}.png", 35),
        MYTHIC(30, "{UndeadMythic}.png", 30),
        LEGENDARY(35, "{UndeadLegendary}.png", 25),
        DIVINE(40, "{UndeadDivine}.png", 20),
        RELIC(45, "{UndeadRelic}.png", 15),
        ANCIENT(50, "{UndeadAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        UndeadRarity(int boostPercentage, String iconPath, int weight) {
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