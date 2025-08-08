package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class UndeadCriticalyPerk extends RarityBasedPerk<UndeadCriticalyPerk.UndeadCriticalyRarity> {

    public UndeadCriticalyPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.CRIT_DAMAGE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        UndeadCriticalyRarity selectedRarity = rollRandomRarity(UndeadCriticalyRarity.class);
        int multiplier = selectedRarity.getMultiplier();
        item.replaceAttribute(AttributeType.CRIT_DAMAGE, AttributeType.PVE_CRIT_DAMAGE, item.get(AttributeType.CRIT_DAMAGE) * multiplier);
        item.replaceAttribute(AttributeType.CRIT_DAMAGE_PERCENT, AttributeType.PVE_CRIT_DAMAGE_PERCENT, item.get(AttributeType.CRIT_DAMAGE_PERCENT));
        item.replaceAttribute(AttributeType.CRIT_CHANCE, AttributeType.PVE_CRIT_CHANCE, item.get(AttributeType.CRIT_CHANCE));
        item.replaceAttribute(AttributeType.TOTAL_CRIT_CHANCE, AttributeType.PVE_CRIT_TOTAL_CHANCE, item.get(AttributeType.TOTAL_CRIT_CHANCE));
        item.replaceAttribute(AttributeType.MULTIPLE_CRIT_CHANCE, AttributeType.PVE_CRIT_MULTIPLE_CHANCE, item.get(AttributeType.MULTIPLE_CRIT_CHANCE));
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum UndeadCriticalyRarity implements RarityBasedPerk.Rarity {
        EPIC(15, "{UndeadcriticalyEpic}.png", 35),
        MYTHICAL(20, "{UndeadcriticalyMythic}.png", 30),
        LEGENDARY(25, "{UndeadcriticalyLegendary}.png", 25),
        DIVINE(30, "{UndeadcriticalyDivine}.png", 20),
        RELIC(35, "{UndeadcriticalyRelic}.png", 15),
        ANCIENT(40, "{UndeadcriticalyAncient}.png", 10);
        private final int multiplier;
        private final String iconPath;
        private final int weight;

        UndeadCriticalyRarity(int setBoost, String iconPath, int weight) {
            this.multiplier = setBoost;
            this.iconPath = iconPath;
            this.weight = weight;
        }

        public int getMultiplier() {
            return multiplier;
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