package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class CriticalPerk extends RarityBasedPerk<CriticalPerk.CriticalRarity> {

    public CriticalPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.CRIT_DAMAGE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        CriticalRarity selectedRarity = rollRandomRarity(CriticalRarity.class);
        int boost = selectedRarity.getBoostPercentage();

        item.multiplyPercent(AttributeType.CRIT_DAMAGE, boost);
        item.multiplyPercent(AttributeType.CRIT_DAMAGE_PERCENT, boost);
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum CriticalRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{CriticalityCommon}.png", 50),
        UNCOMMON(15, "{CriticalityUncommon}.png", 45),
        RARE(20, "{CriticalityRare}.png", 40),
        EPIC(25, "{CriticalityEpic}.png", 35),
        MYTHIC(30, "{CriticalityMythic}.png", 30),
        LEGENDARY(35, "{CriticalityLegendary}.png", 25),
        DIVINE(40, "{CriticalityDivine}.png", 20),
        RELIC(45, "{CriticalityRelic}.png", 15),
        ANCIENT(50, "{CriticalityAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        CriticalRarity(int boostPercentage, String iconPath, int weight) {
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