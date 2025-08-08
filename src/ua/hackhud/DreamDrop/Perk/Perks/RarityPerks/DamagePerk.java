package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class DamagePerk extends RarityBasedPerk<DamagePerk.DamageRarity> {

    public DamagePerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return item.hasAttribute(AttributeType.DAMAGE);
    }

    @Override
    public void applyTo(RPGItemStack item) {
        DamageRarity selectedRarity = rollRandomRarity(DamageRarity.class);
        int boost = selectedRarity.getBoostPercentage();

        item.multiplyPercent(AttributeType.DAMAGE, boost);
        item.multiplyPercent(AttributeType.DAMAGE_PERCENT, boost);
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum DamageRarity implements RarityBasedPerk.Rarity {
        COMMON(10, "{DamageCommon}.png", 50),
        UNCOMMON(15, "{DamageUncommon}.png", 45),
        RARE(20, "{DamageRare}.png", 40),
        EPIC(25, "{DamageEpic}.png", 35),
        MYTHIC(30, "{DamageMythic}.png", 30),
        LEGENDARY(35, "{DamageLegendary}.png", 25),
        DIVINE(40, "{DamageDivine}.png", 20),
        RELIC(45, "{DamageRelic}.png", 15),
        ANCIENT(50, "{DamageAncient}.png", 10);

        private final int boostPercentage;
        private final String iconPath;
        private final int weight;

        DamageRarity(int boostPercentage, String iconPath, int weight) {
            this.boostPercentage = boostPercentage;
            this.iconPath = iconPath;
            this.weight = weight;
        }

        public int getBoostPercentage() {
            return boostPercentage;
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