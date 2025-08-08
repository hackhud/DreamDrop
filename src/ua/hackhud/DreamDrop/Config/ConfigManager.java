package ua.hackhud.DreamDrop.Config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ua.hackhud.DreamDrop.Entity.Rarity;
import ua.hackhud.DreamDrop.Main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ConfigManager {
    private FileConfiguration config;
    private String stringToReplace;
    private String attributeStartString;
    private Pattern attributePattern;
    private final Map<Integer, Rarity> rarities = new HashMap<>();

    public ConfigManager() {
        loadConfig();
    }

    public void loadConfig() {
        File configFile = new File(Main.getPlugin().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            Main.getPlugin().saveResource("config.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
        loadRarities();
        loadStringSection();
    }
    private void loadRarities() {
        ConfigurationSection raritiesSection = config.getConfigurationSection("rarities");
        if (raritiesSection == null) return;

        for (String rarityKey : raritiesSection.getKeys(false)) {
            ConfigurationSection raritySection = raritiesSection.getConfigurationSection(rarityKey);
            if (raritySection == null) continue;

            Rarity rarity = new Rarity(
                    raritySection.getString("hex1"),
                    raritySection.getString("hex2"),
                    raritySection.getString("rarityLore"),
                    raritySection.getString("repairLore")
            );
            rarities.put(Integer.parseInt(rarityKey), rarity);
        }
    }

    public void loadStringSection() {
        stringToReplace = config.getString("stringToReplace");
        String rawPattern = config.getString("attributePattern");
        attributePattern = Pattern.compile(rawPattern);
        attributeStartString = config.getString("attributeStartString");
    }

    public Rarity getRarity(int level) {
        return rarities.get(level);
    }

    public Map<Integer, Rarity> getRarities() {
        return new HashMap<>(rarities);
    }

    public String getStringToReplace() {
        return stringToReplace;
    }
    public Pattern getAttributePattern() {
        return attributePattern;
    }

    public String getAttributeStartString() {
        return attributeStartString;
    }
}
