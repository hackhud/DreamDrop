package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class MultiplicityPerk extends RarityBasedPerk<MultiplicityPerk.MultiplicityRarity> {

    public MultiplicityPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        MultiplicityRarity selectedRarity = rollRandomRarity(MultiplicityRarity.class);
        int setBoost = selectedRarity.getSetBoost();
        item.set(AttributeType.MULTIPLE_CRIT_CHANCE, item.get(AttributeType.MULTIPLE_CRIT_CHANCE) + setBoost);
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum MultiplicityRarity implements RarityBasedPerk.Rarity {
        RARE(2,  "{MultiplicityRare}.png", 40),
        EPIC(4, "{MultiplicityEpic}.png", 35),
        MYTHICAL(6, "{MultiplicityMythic}.png", 30),
        LEGENDARY(8, "{MultiplicityLegendary}.png", 25),
        DIVINE(10, "{MultiplicityDivine}.png", 20),
        RELIC(12, "{MultiplicityRelic}.png", 15),
        ANCIENT(14, "{MultiplicityAncient}.png", 10);
        private final int setBoost;
        private final String iconPath;
        private final int weight;

        MultiplicityRarity(int setBoost, String iconPath, int weight) {
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