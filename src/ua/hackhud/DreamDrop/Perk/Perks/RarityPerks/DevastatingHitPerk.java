package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class DevastatingHitPerk extends RarityBasedPerk<DevastatingHitPerk.DevastatingHitRarity> {

    public DevastatingHitPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        DevastatingHitRarity selectedRarity = rollRandomRarity(DevastatingHitRarity.class);
        double setBoost = selectedRarity.getSetBoost();
        item.set(AttributeType.DAMAGE_HEALTH_NPC, item.get(AttributeType.DAMAGE_HEALTH_NPC) + setBoost);

        item.addIcon(selectedRarity.getIconPath());
    }

    public enum DevastatingHitRarity implements RarityBasedPerk.Rarity {
        RELIC(0.1, "{DevastatinghitRelic}.png", 15),
        ANCIENT(0.2, "{DevastatinghitAncient}.png", 10);
        private final double setBoost;
        private final String iconPath;
        private final int weight;

        DevastatingHitRarity(double setBoost, String iconPath, int weight) {
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