package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class DoubleDamagePerk extends RarityBasedPerk<DoubleDamagePerk.DoubleDamageRarity> {

    public DoubleDamagePerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.CRIT_DAMAGE)
                || item.hasAttribute(AttributeType.CRIT_DAMAGE_PERCENT)
                || item.hasAttribute(AttributeType.DAMAGE)
                || item.hasAttribute(AttributeType.DAMAGE_PERCENT)
                || item.hasAttribute(AttributeType.PVE_DAMAGE)
                || item.hasAttribute(AttributeType.PVE_DAMAGE_PERCENT)
                || item.hasAttribute(AttributeType.CLEAN_PVE_DAMAGE)
                || item.hasAttribute(AttributeType.PVE_CRIT_DAMAGE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        DoubleDamageRarity selectedRarity = rollRandomRarity(DoubleDamageRarity.class);
        double setBoost = selectedRarity.getSetBoost();

        if (item.hasAttribute(AttributeType.CRIT_DAMAGE) || item.hasAttribute(AttributeType.CRIT_DAMAGE_PERCENT)
        || item.hasAttribute(AttributeType.DAMAGE) || item.hasAttribute(AttributeType.DAMAGE_PERCENT)) {
            item.set(AttributeType.DOUBLE_PVP, item.get(AttributeType.DOUBLE_PVP) + setBoost);
        }

        if (item.hasAttribute(AttributeType.PVE_DAMAGE) || item.hasAttribute(AttributeType.PVE_DAMAGE_PERCENT)
        || item.hasAttribute(AttributeType.CLEAN_PVE_DAMAGE) || item.hasAttribute(AttributeType.PVE_CRIT_DAMAGE)) {
            item.set(AttributeType.DOUBLE_PVE, item.get(AttributeType.DOUBLE_PVE) + setBoost * 2);
        }

        item.addIcon(selectedRarity.getIconPath());
    }

    public enum DoubleDamageRarity implements RarityBasedPerk.Rarity {
        MYTHICAL(0.3, "{DoubledamageMythic}.png", 30),
        LEGENDARY(0.5, "{DoubledamageLegendary}.png", 25),
        DIVINE(0.7, "{DoubledamageDivine}.png", 20),
        RELIC(0.9, "{DoubledamageRelic}.png", 15),
        ANCIENT(1.2, "{DoubledamageAncient}.png", 10);
        private final double setBoost;
        private final String iconPath;
        private final int weight;

        DoubleDamageRarity(double setBoost, String iconPath, int weight) {
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