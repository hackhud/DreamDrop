package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class ReconstructionPerk extends RarityBasedPerk<ReconstructionPerk.ReconstructionRarity> {

    public ReconstructionPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        ReconstructionRarity selectedRarity = rollRandomRarity(ReconstructionRarity.class);
        int percentBoost = selectedRarity.getPercentBoost();
        int setBoost = selectedRarity.getSetBoost();

        double currentValue = item.get(AttributeType.AUTO_REPAIR);
        if (currentValue > 0) {
            item.multiplyPercent(AttributeType.AUTO_REPAIR, percentBoost);
        } else {
            item.set(AttributeType.AUTO_REPAIR, currentValue + setBoost);
        }
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum ReconstructionRarity implements RarityBasedPerk.Rarity {
        DIVINE(15, 6, "{ReconstructionDivine}.png", 20),
        RELIC(20, 8, "{ReconstructionRelic}.png", 15),
        ANCIENT(30, 11, "{ReconstructionAncient}.png", 10);

        private final int percentBoost;
        private final int setBoost;
        private final String iconPath;
        private final int weight;

        ReconstructionRarity(int percentBoost, int setBoost, String iconPath, int weight) {
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