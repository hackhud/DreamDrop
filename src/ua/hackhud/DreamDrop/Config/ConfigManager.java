package ua.hackhud.DreamDrop.Config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ua.hackhud.DreamDrop.Entity.Rarity;
import ua.hackhud.DreamDrop.Main;

import java.io.File;
import java.util.Collections;
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

    public Rarity getRarity(int level) {
        return rarities.get(level);
    }

    public Map<Integer, Rarity> getRarities() {
        return Collections.unmodifiableMap(rarities);
    }

    public String getStringToReplace() {
        return stringToReplace;
    }

    public String getAttributeStartString() {
        return attributeStartString;
    }

    public Pattern getAttributePattern() {
        return attributePattern;
    }

    public void reload() {
        rarities.clear();
        loadConfig();
    }

    private void loadConfig() {
        File configFile = getConfigFile();
        ensureConfigExists(configFile);
        this.config = YamlConfiguration.loadConfiguration(configFile);
        loadRarities();
        loadStringSettings();
    }

    private File getConfigFile() {
        return new File(Main.getPlugin().getDataFolder(), "config.yml");
    }

    private void ensureConfigExists(File configFile) {
        if (!configFile.exists()) {
            Main.getPlugin().saveResource("config.yml", false);
        }
    }

    private void loadRarities() {
        ConfigurationSection raritiesSection = config.getConfigurationSection("rarities");
        if (raritiesSection == null) return;
        for (String rarityKey : raritiesSection.getKeys(false)) {
            ConfigurationSection raritySection = raritiesSection.getConfigurationSection(rarityKey);
            if (raritySection == null) continue;
            Rarity rarity = createRarityFromConfig(raritySection);
            rarities.put(Integer.parseInt(rarityKey), rarity);
        }
    }

    private Rarity createRarityFromConfig(ConfigurationSection section) {
        return new Rarity(
                section.getString("hex1"),
                section.getString("hex2"),
                section.getString("rarityLore"),
                section.getString("repairLore")
        );
    }

    private void loadStringSettings() {
        this.stringToReplace = config.getString("stringToReplace");
        this.attributeStartString = config.getString("attributeStartString");
        String rawPattern = config.getString("attributePattern");
        if (rawPattern != null) {
            this.attributePattern = Pattern.compile(rawPattern);
        }
    }
}
