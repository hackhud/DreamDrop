package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class DurabilityPerk extends RarityBasedPerk<DurabilityPerk.DurabilityRarity> {

    public DurabilityPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        DurabilityRarity selectedRarity = rollRandomRarity(DurabilityRarity.class);
        int percentBoost = selectedRarity.getPercentBoost();
        int setBoost = selectedRarity.getSetBoost();

        double currentValue = item.get(AttributeType.ADDITIONAL_DURABILITY);
        if (currentValue > 0) {
            item.multiplyPercent(AttributeType.ADDITIONAL_DURABILITY, percentBoost);
        } else {
            item.set(AttributeType.ADDITIONAL_DURABILITY, currentValue + setBoost);
        }
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum DurabilityRarity implements RarityBasedPerk.Rarity {
        RARE(15, 10, "{DurabilityRare}.png", 40),
        EPIC(20, 15, "{DurabilityEpic}.png", 35),
        MYTHICAL(25, 20, "{DurabilityMythic}.png", 30),
        LEGENDARY(30, 25, "{DurabilityLegendary}.png", 25),
        DIVINE(35, 30, "{DurabilityDivine}.png", 20),
        RELIC(40, 35, "{DurabilityRelic}.png", 15),
        ANCIENT(50, 40, "{DurabilityAncient}.png", 10);

        private final int percentBoost;
        private final int setBoost;
        private final String iconPath;
        private final int weight;

        DurabilityRarity(int percentBoost, int setBoost, String iconPath, int weight) {
            this.percentBoost = percentBoost;
            this.setBoost = setBoost;
            this.iconPath = iconPath;
            this.weight = weight;
        }

        public int getPercentBoost() {
            return percentBoost;
        }

        public int getSetBoost() {
            return setBoost;
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