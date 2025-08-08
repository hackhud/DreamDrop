package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class LuckPerk extends RarityBasedPerk<LuckPerk.LuckRarity> {

    public LuckPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        LuckRarity selectedRarity = rollRandomRarity(LuckRarity.class);
        double setBoost = selectedRarity.getSetBoost();
        item.set(AttributeType.NPC_DROP_CHANCE, item.get(AttributeType.NPC_DROP_CHANCE) + setBoost);

        item.addIcon(selectedRarity.getIconPath());
    }

    public enum LuckRarity implements RarityBasedPerk.Rarity {

        LEGENDARY(0.5, "{LuckLegendary}.png", 25),
        DIVINE(0.8, "{LuckDivine}.png", 20),
        RELIC(1.0, "{LuckRelic}.png", 15),
        ANCIENT(1.5, "{LuckAncient}.png", 10);
        private final double setBoost;
        private final String iconPath;
        private final int weight;

        LuckRarity(double setBoost, String iconPath, int weight) {
            this.setBoost = setBoost;
            this.iconPath = iconPath;
            this.weight = weight;
        }

        public double getSetBoost() {
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