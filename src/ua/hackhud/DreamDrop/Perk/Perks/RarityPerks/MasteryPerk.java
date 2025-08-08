package ua.hackhud.DreamDrop.Perk.Perks.RarityPerks;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.AttributeType;

public class MasteryPerk extends RarityBasedPerk<MasteryPerk.MasteryRarity> {

    public MasteryPerk(int baseWeight, String perkId) {
        super(baseWeight, perkId);
    }

    @Override
    public boolean canApplyTo(RPGItemStack item) {
        return true;
    }

    @Override
    public void applyTo(RPGItemStack item) {
        MasteryRarity selectedRarity = rollRandomRarity(MasteryRarity.class);
        int setBoost = selectedRarity.getSetBoost();
        item.set(AttributeType.MCMMO, item.get(AttributeType.MCMMO) + setBoost);
        item.addIcon(selectedRarity.getIconPath());
    }

    public enum MasteryRarity implements RarityBasedPerk.Rarity {
        UNCOMMON(10, "{MasteryUncommon}.png", 50),
        RARE(15,  "{MasteryRare}.png", 40),
        EPIC(20, "{MasteryEpic}.png", 35),
        MYTHICAL(25, "{MasteryMythic}.png", 30),
        LEGENDARY(40, "{MasteryLegendary}.png", 25),
        DIVINE(50, "{MasteryDivine}.png", 20),
        RELIC(60, "{MasteryRelic}.png", 15),
        ANCIENT(50, "{MasteryAncient}.png", 10);
        private final int setBoost;
        private final String iconPath;
        private final int weight;

        MasteryRarity(int setBoost, String iconPath, int weight) {
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