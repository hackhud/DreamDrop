package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class PenetratingHitPerk extends RarityBasedPerk<PenetratingHitPerk.PenetratingHitRarity> {

    public PenetratingHitPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        PenetratingHitRarity selectedRarity = rollRandomRarity(PenetratingHitRarity.class);
        int setBoost = selectedRarity.getSetBoost();
        item.set(AttributeType.SHIELD_PENETRATION, item.get(AttributeType.SHIELD_PENETRATION) + setBoost);
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum PenetratingHitRarity implements RarityBasedPerk.Rarity {
        MYTHICAL(5, "{PenetratinghitMythic}.png", 30),
        LEGENDARY(7, "{PenetratinghitLegendary}.png", 25),
        DIVINE(10, "{PenetratinghitDivine}.png", 20),
        RELIC(13, "{PenetratinghitRelic}.png", 15),
        ANCIENT(16, "{PenetratinghitAncient}.png", 10);
        private final int setBoost;
        private final String iconPath;
        private final int weight;

        PenetratingHitRarity(int setBoost, String iconPath, int weight) {
            this.setBoost = setBoost;
            this.iconPath = iconPath;
            this.weight = weight;
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