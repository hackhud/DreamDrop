package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class ResistancePerk extends RarityBasedPerk<ResistancePerk.ResistanceRarity> {

    public ResistancePerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        ResistanceRarity selectedRarity = rollRandomRarity(ResistanceRarity.class);
        int setBoost = selectedRarity.getSetBoost();
        item.set(AttributeType.TOTAL_ANTI_DMG_CHANCE, item.get(AttributeType.TOTAL_ANTI_DMG_CHANCE) + setBoost);
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum ResistanceRarity implements RarityBasedPerk.Rarity {
        EPIC(3, "{ResistanceEpic}.png", 35),
        MYTHICAL(4, "{ResistanceMythic}.png", 30),
        LEGENDARY(5, "{ResistanceLegendary}.png", 25),
        DIVINE(6, "{ResistanceDivine}.png", 20),
        RELIC(7, "{ResistanceRelic}.png", 15),
        ANCIENT(8, "{ResistanceAncient}.png", 10);
        private final int setBoost;
        private final String iconPath;
        private final int weight;

        ResistanceRarity(int setBoost, String iconPath, int weight) {
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