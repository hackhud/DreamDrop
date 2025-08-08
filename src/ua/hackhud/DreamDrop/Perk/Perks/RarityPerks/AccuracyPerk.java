package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class AccuracyPerk extends RarityBasedPerk<AccuracyPerk.AccuracyRarity> {

    public AccuracyPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        AccuracyRarity selectedRarity = rollRandomRarity(AccuracyRarity.class);
        int percentBoost = selectedRarity.getPercentBoost();
        int setBoost = selectedRarity.getSetBoost();

        double currentValue = item.get(AttributeType.ACCURACY);
        if (currentValue > 0) {
            item.multiplyPercent(AttributeType.ACCURACY, percentBoost);
        } else {
            item.set(AttributeType.ACCURACY, currentValue + setBoost);
        }
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum AccuracyRarity implements RarityBasedPerk.Rarity {
        UNCOMMON(10, 10, "{AccuracyUncommon}.png", 50),
        RARE(15, 15, "{AccuracyRare}.png", 40),
        EPIC(20, 20, "{AccuracyEpic}.png", 35),
        MYTHICAL(25, 30, "{AccuracyMythic}.png", 30),
        LEGENDARY(40, 30, "{AccuracyLegendary}.png", 25),
        DIVINE(50, 35, "{AccuracyDivine}.png", 20),
        RELIC(60, 40, "{AccuracyRelic}.png", 15),
        ANCIENT(50, 70, "{AccuracyAncient}.png", 10);

        private final int percentBoost;
        private final int setBoost;
        private final String iconPath;
        private final int weight;

        AccuracyRarity(int percentBoost, int setBoost, String iconPath, int weight) {
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