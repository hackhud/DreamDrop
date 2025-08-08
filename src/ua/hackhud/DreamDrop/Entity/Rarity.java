package ua.hackhud.DreamDrop.Entity;

public class Rarity {
    private final String hex1;
    private final String hex2;
    private final String rarityLore;
    private final String repairLore;

    public Rarity(String hex1, String hex2, String rarityLore, String repairLore) {
        this.hex1 = hex1;
        this.hex2 = hex2;
        this.rarityLore = rarityLore;
        this.repairLore = repairLore;
    }

    public String getHex1() {
        return hex1;
    }

    public String getHex2() {
        return hex2;
    }

    public String getRarityLore() {
        return rarityLore;
    }

    public String getRepairLore() {
        return repairLore;
    }
}